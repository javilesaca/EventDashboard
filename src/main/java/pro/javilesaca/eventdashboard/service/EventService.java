package pro.javilesaca.eventdashboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import pro.javilesaca.eventdashboard.dto.EventDTO;
import pro.javilesaca.eventdashboard.repository.EventRepository;
import pro.javilesaca.eventdashboard.model.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final MongoTemplate mongoTemplate;

    public EventService(EventRepository eventRepository, MongoTemplate mongoTemplate) {
        this.eventRepository = eventRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Event saveEvent(EventDTO dto) {
        LocalDateTime ts = dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now();
        Event e = new Event(dto.getType(), dto.getMessage(), dto.getSource(), ts);
        return eventRepository.save(e);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    public Event getById(String id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    public Page<Event> search(String type, String source, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        List<Criteria> criteria = new ArrayList<>();
        if (type != null && !type.isBlank()) {
            criteria.add(Criteria.where("type").is(type));
        }
        if (source != null && !source.isBlank()) {
            criteria.add(Criteria.where("source").is(source));
        }
        if (from != null && to != null) {
            criteria.add(Criteria.where("timestamp").gte(from).lte(to));
        } else if (from != null) {
            criteria.add(Criteria.where("timestamp").gte(from));
        } else if (to != null) {
            criteria.add(Criteria.where("timestamp").lte(to));
        }
        Query query = criteria.isEmpty()
                ? new Query()
                : new Query(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        long count = mongoTemplate.count(query, Event.class);
        List<Event> events = mongoTemplate.find(query.with(pageable), Event.class);
        return PageableExecutionUtils.getPage(events, pageable, () -> count);
    }
}
