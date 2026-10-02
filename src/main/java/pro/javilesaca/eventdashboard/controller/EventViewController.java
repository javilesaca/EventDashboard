package pro.javilesaca.eventdashboard.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pro.javilesaca.eventdashboard.service.EventService;

@Controller
public class EventViewController {

    private final EventService eventService;

    public EventViewController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String showEvents(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String source,
            Model model) {
        model.addAttribute("events", eventService.search(
                type, source, null, null,
                PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "timestamp"))).getContent());
        model.addAttribute("counts", eventService.countByType());
        model.addAttribute("type", type);
        model.addAttribute("source", source);
        return "events";
    }

}
