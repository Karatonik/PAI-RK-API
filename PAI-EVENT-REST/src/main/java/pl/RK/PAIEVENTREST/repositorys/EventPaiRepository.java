package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.UserPAI;

import java.util.List;
import java.util.Set;

@Repository
public interface EventPaiRepository extends JpaRepository<EventPAI , Integer> {


    public List<EventPAI> findByNameAndAddress(String name, String address);
}
