package pro.javilesaca.eventdashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.javilesaca.eventdashboard.dto.EventDTO;
import pro.javilesaca.eventdashboard.service.EventService;
import pro.javilesaca.eventdashboard.model.Event;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Eventos (Event)", description = "Operaciones relacionadas con el registro y consulta de eventos del sistema.")
@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }


    @Operation(
            summary = "Registrar un nuevo evento",
            description = "Recibe un objeto con los detalles del evento y lo guarda en la base de datos MongoDB."
    )
    @PostMapping
    public ResponseEntity<Event> logEvent(@RequestBody EventDTO dto) {
        return ResponseEntity.ok(service.saveEvent(dto));
    }

    @Operation(
            summary = "Listar todos los eventos",
            description = "Devuelve una lista ordenada de todos los eventos registrados en la base de datos."
    )
    @GetMapping
    public List<Event> listEvents() {
        return service.getAllEvents();
    }
}
