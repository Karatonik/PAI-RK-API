package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.RK.PAIEVENTREST.models.Participation;

public interface ParticipationRepository extends JpaRepository<Participation , Long> {
}
