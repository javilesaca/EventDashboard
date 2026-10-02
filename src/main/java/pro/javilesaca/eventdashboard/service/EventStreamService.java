package pro.javilesaca.eventdashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pro.javilesaca.eventdashboard.model.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class EventStreamService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));
        return emitter;
    }

    public void publish(Event event) {
        emitters.removeIf(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("event").data(event));
                return false;
            } catch (Exception e) {
                emitter.complete();
                return true;
            }
        });
    }
}
