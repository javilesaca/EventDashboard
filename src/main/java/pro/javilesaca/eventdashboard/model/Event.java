package pro.javilesaca.eventdashboard.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String type;
    private String message;
    private String source;
    private LocalDateTime timestamp;

    //Constructor
    public Event(String type, String message, String source, LocalDateTime timestamp) {
        this.type = type;
        this.message = message;
        this.source = source;
        this.timestamp = timestamp;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}


