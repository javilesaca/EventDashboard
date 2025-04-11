package pro.javilesaca.eventdashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.javilesaca.eventdashboard.model.EventLog;

import java.util.List;

@RestController
public class EventLogController {

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
                            schema = @Schema(implementation = EventLog.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de eventos",
                                    summary = "Listado de eventos",
                                    value = """
                                    [
                                        {
                                            "id": "1",
                                            "type": "LOGIN_SUCCESS",
                                            "timestamp": "2025-04-10T10:15:30",
                                            "description": "Usuario admin inició sesión con éxito"
                                        },
                                        {
                                            "id": "2",
                                            "type": "ERROR",
                                            "timestamp": "2025-04-10T10:17:00",
                                            "description": "Error al intentar guardar configuración"
                                        }
                                    ]
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/events")
    public List<EventLog> getAllEvents() {
        return List.of(
                new EventLog("1", "LOGIN_SUCCESS", "2025-04-10T10:15:30", "Usuario admin inició sesión con éxito"),
                new EventLog("2", "ERROR", "2025-04-10T10:17:00", "Error al intentar guardar configuración")
        );
    }
}
