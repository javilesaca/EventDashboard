package pro.javilesaca.eventdashboard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pro.javilesaca.eventdashboard.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, Long> {
    // Buscar todos los eventos de un tipo específico
    List<Event> findByType(String type);

    // Buscar por tipo y ordenar por fecha descendente
    List<Event> findByTypeOrderByTimestampDesc(String type);

    // Buscar eventos de un servicio específico (source)
    List<Event> findBySource(String source);

    // Buscar eventos entre dos fechas
    List<Event> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Buscar eventos recientes (últimos X minutos, se filtra en el controller)
    List<Event> findTop50ByOrderByTimestampDesc();

    // Buscar por tipo y rango de tiempo
    List<Event> findByTypeAndTimestampBetween(String type, LocalDateTime start, LocalDateTime end);

    // Buscar eventos con tipo "ERROR"
    List<Event> findByTypeIgnoreCase(String type); // Más robusto si se envían en minúsculas

    // Buscar eventos cuyo mensaje contiene cierta palabra clave
    List<Event> findByMessageContainingIgnoreCase(String keyword);

    // Buscar eventos que no sean INFO, por ejemplo
    List<Event> findByTypeNot(String excludedType);
}

