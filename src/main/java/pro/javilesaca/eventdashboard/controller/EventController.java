package pro.javilesaca.eventdashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
    public ResponseEntity<Event> logEvent(@Valid @RequestBody EventDTO dto) {
        Event saved = service.saveEvent(dto);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri()
        ).body(saved);
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
