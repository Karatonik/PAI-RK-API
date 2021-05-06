package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.FileDB;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.TypeOfImage;

import java.util.Optional;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB,Integer> {


    Optional<FileDB> findByUserPAIAndTypeOfImage(UserPAI userPAI, TypeOfImage typeOfImage);

    Optional<FileDB> findByEventPAI(EventPAI eventPAI);
}
