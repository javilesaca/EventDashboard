package pro.javilesaca.eventdashboard.controller;

import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import pro.javilesaca.eventdashboard.model.Event;
import pro.javilesaca.eventdashboard.model.EventLog;
import pro.javilesaca.eventdashboard.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/docs/events")
public class EventLogController {

    private final EventService service;

    public EventLogController(EventService service) {
        this.service = service;
    }

    @Operation(
            summary = "Obtener todos los eventos registrados",
            description = "Devuelve una lista de todos los eventos registrados en el sistema, incluyendo tipo, fecha y detalles."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de eventos obtenido correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EventLog.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public List<EventLog> getAllEventLogs() {
        List<Event> events = service.getAllEvents();

        return events.stream()
                .map(event -> new EventLog(
                        event.getId(),
                        event.getType(),
                        event.getTimestamp().toString(),
                        event.getMessage()
                ))
                .collect(Collectors.toList());
    }
}
