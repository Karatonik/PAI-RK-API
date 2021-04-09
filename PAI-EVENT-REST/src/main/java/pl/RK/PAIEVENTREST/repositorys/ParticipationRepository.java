package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation , Integer> {

    public  Set<Participation> findByEventPAI(EventPAI eventPAI);

    public Set<Participation> findByUserPAI(UserPAI userPAI);

    public Optional<Participation> findByUserPAIAndEventPAI(UserPAI userPAI, EventPAI eventPAI);

}
