package pro.javilesaca.eventdashboard.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa un evento registrado en el sistema, como logueos, errores, etc.")
public class EventLog {

    @Schema(description = "ID único del evento", example = "1", required = true)
    private String id;

    @Schema(description = "Tipo de evento, como 'LOGIN_SUCCESS' o 'ERROR'", example = "LOGIN_SUCCESS", required = true)
    private String type;

    @Schema(description = "Fecha y hora en que ocurrió el evento", example = "2025-04-10T10:15:30", required = true)
    private String timestamp;

    @Schema(description = "Descripción del evento", example = "Usuario admin inició sesión con éxito", required = true)
    private String description;

    // Constructor
    public EventLog(String id, String type, String timestamp, String description) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
