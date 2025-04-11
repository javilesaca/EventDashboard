package pro.javilesaca.eventdashboard.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private String type;
    private String message;
    private String source;
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

