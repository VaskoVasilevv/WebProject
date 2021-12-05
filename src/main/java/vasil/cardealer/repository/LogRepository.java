package vasil.cardealer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasil.cardealer.models.entity.Log;

import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {



}
