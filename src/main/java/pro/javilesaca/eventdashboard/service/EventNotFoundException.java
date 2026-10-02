package pro.javilesaca.eventdashboard.service;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String id) {
        super("Evento no encontrado: " + id);
    }
}
