package pro.javilesaca.eventdashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pro.javilesaca.eventdashboard.service.EventService;

@Controller
public class EventViewController {

    private final EventService eventService;

    public EventViewController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String showEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events";  // Esto devolverá la vista 'events.html'
    }

}
