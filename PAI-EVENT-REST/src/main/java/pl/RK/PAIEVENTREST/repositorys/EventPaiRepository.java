package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.RK.PAIEVENTREST.models.EventPAI;

import java.util.List;

@Repository
public interface EventPaiRepository extends JpaRepository<EventPAI , Integer> {

    public List<EventPAI> findByNameAndProvinceAndCityAndAddress(String name,String province,String city, String address);

    public List<EventPAI> findAllByCity(String city);
}
