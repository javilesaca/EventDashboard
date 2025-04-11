package pro.javilesaca.eventdashboard.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pro.javilesaca.eventdashboard.dto.EventDTO;
import pro.javilesaca.eventdashboard.repository.EventRepository;
import pro.javilesaca.eventdashboard.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(EventDTO dto) {
        LocalDateTime ts = dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now();
        Event e = new Event(dto.getType(), dto.getMessage(), dto.getSource(), ts);
        return eventRepository.save(e);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}
