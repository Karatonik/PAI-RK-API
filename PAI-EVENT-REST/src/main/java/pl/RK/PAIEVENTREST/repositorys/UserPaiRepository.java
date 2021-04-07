package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.RK.PAIEVENTREST.models.UserPAI;

public interface UserPaiRepository extends JpaRepository<UserPAI , Long> {
}
