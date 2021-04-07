package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.RK.PAIEVENTREST.models.EventPAI;

public interface EventPaiRepository extends JpaRepository<EventPAI , Long> {
}
