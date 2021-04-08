package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.RK.PAIEVENTREST.models.Participation;
@Repository
public interface ParticipationRepository extends JpaRepository<Participation , Integer> {
}
