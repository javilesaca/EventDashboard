package pro.javilesaca.eventdashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.javilesaca.eventdashboard.dto.EventDTO;
import pro.javilesaca.eventdashboard.service.EventService;
import pro.javilesaca.eventdashboard.model.Event;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Event> logEvent(@RequestBody EventDTO dto) {
        return ResponseEntity.ok(service.saveEvent(dto));
    }

    @GetMapping
    public List<Event> listEvents() {
        return service.getAllEvents();
    }
}
