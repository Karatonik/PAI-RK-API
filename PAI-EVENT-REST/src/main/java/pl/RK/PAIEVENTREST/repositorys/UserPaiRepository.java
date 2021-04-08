package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.RK.PAIEVENTREST.models.UserPAI;

import java.util.Optional;
@Repository
public interface UserPaiRepository extends JpaRepository<UserPAI , String> {

    Optional<UserPAI> findByUserKey( String key);
}
