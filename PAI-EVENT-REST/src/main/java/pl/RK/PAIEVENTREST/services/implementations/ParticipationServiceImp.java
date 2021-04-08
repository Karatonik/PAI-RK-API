package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.repositorys.ParticipationRepository;
import pl.RK.PAIEVENTREST.services.interfaces.ParticipationServiceIF;
@Service
public class ParticipationServiceImp implements ParticipationServiceIF {

    ParticipationRepository participationRepository;
    @Autowired
    public ParticipationServiceImp(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }


}
