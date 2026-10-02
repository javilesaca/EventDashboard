package pro.javilesaca.eventdashboard.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import pro.javilesaca.eventdashboard.dto.EventDTO;
import pro.javilesaca.eventdashboard.model.Event;
import pro.javilesaca.eventdashboard.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private EventStreamService streamService;

    @InjectMocks
    private EventService service;

    private static EventDTO dto(String type, String message, String source, LocalDateTime ts) {
        EventDTO dto = new EventDTO();
        dto.setType(type);
        dto.setMessage(message);
        dto.setSource(source);
        dto.setTimestamp(ts);
        return dto;
    }

    @Test
    void saveEventDefaultsTimestampWhenMissingAndPublishes() {
        when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        Event saved = service.saveEvent(dto("INFO", "arranque", "system", null));

        assertThat(saved.getTimestamp()).isNotNull();
        ArgumentCaptor<Event> published = ArgumentCaptor.forClass(Event.class);
        verify(streamService).publish(published.capture());
        assertThat(published.getValue().getType()).isEqualTo("INFO");
    }

    @Test
    void saveEventKeepsProvidedTimestamp() {
        LocalDateTime ts = LocalDateTime.of(2025, 4, 12, 12, 0, 0);
        when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        Event saved = service.saveEvent(dto("ERROR", "fallo", "backend", ts));

        assertThat(saved.getTimestamp()).isEqualTo(ts);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(eventRepository.findById("nope")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById("nope"))
                .isInstanceOf(EventNotFoundException.class)
                .hasMessageContaining("nope");
    }

    @Test
    void searchAppliesFiltersAndPagination() {
        Event event = new Event("ERROR", "fallo", "backend", LocalDateTime.now());
        Pageable pageable = PageRequest.of(0, 20);
        when(mongoTemplate.count(any(Query.class), eq(Event.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Event.class))).thenReturn(List.of(event));

        Page<Event> page = service.search("ERROR", "backend", null, null, pageable);

        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).containsExactly(event);
    }
}
