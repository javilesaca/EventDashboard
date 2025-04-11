package pro.javilesaca.eventdashboard.repository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Podrías añadir búsqueda por tipo, fecha, etc.
}

