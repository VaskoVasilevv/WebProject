package vasil.cardealer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasil.cardealer.models.entity.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {

    List<Event> findAllByUser_Username(String name);

    Optional<Event> findById(String id);

    Optional<Event> findByName(String name);

}
