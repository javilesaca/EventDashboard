package pro.javilesaca.eventdashboard.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pro.javilesaca.eventdashboard.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Carga datos de ejemplo al arrancar con el perfil {@code demo}:
 * {@code SPRING_PROFILES_ACTIVE=demo ./mvnw spring-boot:run}.
 */
@Component
@Profile("demo")
public class DemoSeeder implements CommandLineRunner {

    private static final List<String[]> SAMPLES = List.of(
            new String[]{"INFO", "Usuario admin inició sesión", "frontend"},
            new String[]{"INFO", "Despliegue v1.4.2 completado", "deploys"},
            new String[]{"WARNING", "Latencia p99 por encima de 800ms", "backend"},
            new String[]{"ERROR", "Timeout conectando con MongoDB", "backend"},
            new String[]{"INFO", "Nuevo registro de usuario", "frontend"},
            new String[]{"ERROR", "Pago rechazado por el proveedor", "payments"},
            new String[]{"WARNING", "Disco al 85% en event-mongo", "infra"},
            new String[]{"INFO", "Backup nocturno completado", "infra"}
    );

    private final EventService eventService;

    public DemoSeeder(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void run(String... args) {
        if (!eventService.search(null, null, null, null,
                org.springframework.data.domain.Pageable.ofSize(1)).isEmpty()) {
            return;
        }
        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            String[] sample = SAMPLES.get(random.nextInt(SAMPLES.size()));
            EventDTO dto = new EventDTO();
            dto.setType(sample[0]);
            dto.setMessage(sample[1]);
            dto.setSource(sample[2]);
            dto.setTimestamp(LocalDateTime.now().minusMinutes(random.nextInt(60 * 24)));
            eventService.saveEvent(dto);
        }
    }
}
