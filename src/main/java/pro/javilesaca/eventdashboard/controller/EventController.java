package pro.javilesaca.eventdashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pro.javilesaca.eventdashboard.dto.EventDTO;
import pro.javilesaca.eventdashboard.service.EventService;
import pro.javilesaca.eventdashboard.service.EventStreamService;
import pro.javilesaca.eventdashboard.model.Event;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Eventos (Event)", description = "Operaciones relacionadas con el registro y consulta de eventos del sistema.")
@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;
    private final EventStreamService streamService;

    public EventController(EventService service, EventStreamService streamService) {
        this.service = service;
        this.streamService = streamService;
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
            summary = "Listar eventos con filtros y paginación",
            description = "Devuelve una página de eventos ordenados por timestamp descendente. Todos los filtros son opcionales."
    )
    @GetMapping
    public Page<Event> listEvents(
            @Parameter(description = "Filtrar por tipo exacto", example = "ERROR")
            @RequestParam(required = false) String type,
            @Parameter(description = "Filtrar por origen exacto", example = "backend")
            @RequestParam(required = false) String source,
            @Parameter(description = "Desde (ISO-8601)", example = "2025-01-01T00:00:00")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @Parameter(description = "Hasta (ISO-8601)", example = "2025-12-31T23:59:59")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @ParameterObject @PageableDefault(size = 20, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.search(type, source, from, to, pageable);
    }

    @Operation(
            summary = "Obtener un evento por id",
            description = "Devuelve 404 si el evento no existe."
    )
    @GetMapping("/{id}")
    public Event getEvent(@PathVariable String id) {
        return service.getById(id);
    }

    @Operation(
            summary = "Suscribirse al flujo de eventos en tiempo real",
            description = "Abre un canal Server-Sent Events. Cada evento creado se emite a todos los suscriptores."
    )
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamEvents() {
        return streamService.subscribe();
    }
}
