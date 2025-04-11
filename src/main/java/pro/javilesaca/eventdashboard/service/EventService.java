package pro.javilesaca.eventdashboard.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pro.javilesaca.eventdashboard.dto.EventDTO;
import pro.javilesaca.eventdashboard.repository.EventRepository;

import java.time.LocalDateTime;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event saveEvent(EventDTO dto) {
        Event e = new Event();
        e.setType(dto.getType());
        e.setMessage(dto.getMessage());
        e.setSource(dto.getSource());
        e.setTimestamp(LocalDateTime.now());
        return repository.save(e);
    }

    public List<Event> getAllEvents() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}
