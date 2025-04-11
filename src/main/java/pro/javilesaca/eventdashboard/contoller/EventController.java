package pro.javilesaca.eventdashboard.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
