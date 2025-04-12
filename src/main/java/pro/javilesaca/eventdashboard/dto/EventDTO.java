package pro.javilesaca.eventdashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Objeto que representa un evento que será registrado en el sistema.")
public class EventDTO {

    @Schema(description = "Tipo de evento", example = "LOGIN_SUCCESS")
    private String type;

    @Schema(description = "Mensaje descriptivo del evento", example = "Usuario admin inició sesión")
    private String message;

    @Schema(description = "Origen del evento", example = "frontend")
    private String source;

    @Schema(description = "Fecha y hora del evento", example = "2025-04-12T12:00:00")
    private LocalDateTime timestamp;

    //Getters y Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

