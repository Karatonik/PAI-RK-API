package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.RequestFrom;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.repositorys.ParticipationRepository;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.ParticipationServiceIF;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParticipationServiceImp implements ParticipationServiceIF {

    ParticipationRepository participationRepository;
    EventPaiRepository eventPaiRepository;
    UserPaiRepository userPaiRepository;


    @Autowired
    public ParticipationServiceImp(ParticipationRepository participationRepository
            ,EventPaiRepository eventPaiRepository,UserPaiRepository userPaiRepository) {
        this.participationRepository = participationRepository;
        this.eventPaiRepository=eventPaiRepository;
        this.userPaiRepository=userPaiRepository;
    }

    //Zaproszenia użytkownika (tylko moje nie eventu) ,
    // Widać gdy
    // Ja chcę dołączyć do eventu
    //Event mnie zaprosi
    @Override
    public List<Participation> userParticipationSet(String email) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        return optionalUserPAI.map(userPAI -> participationRepository.findByUserPAI(userPAI)
                .stream().filter(v -> v.getRequest().equals(RequestFrom.User)).collect(Collectors.toList())).orElse(null);
    }


    //Zaproszenie przez event
    //Widać gdy
    //w widkou zapraszania w  event
    //osoby zaproszone przez event
    @Override
    public List<Participation> eventParticipationSet(int eventId) {
        Optional<EventPAI>optionalEventPAI = eventPaiRepository.findById(eventId);
        return optionalEventPAI.map(eventPAI -> participationRepository.findByEventPAI(eventPAI)
                .stream().filter(v->v.getRequest().equals(RequestFrom.Event)).collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<Participation> participationFromEventToUser(String email) {
        Optional<UserPAI> optionalUserPAI=userPaiRepository.findById(email);
        return optionalUserPAI.map(userPAI -> participationRepository.findByUserPAI(userPAI)
                .stream().filter(v -> v.getRequest().equals(RequestFrom.Event)).collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<Participation> participationFromUserToEvent(int eventId) {
        Optional<EventPAI>optionalEventPAI = eventPaiRepository.findById(eventId);
        return optionalEventPAI.map(eventPAI -> participationRepository.findByEventPAI(eventPAI)
                .stream().filter(v->v.getRequest().equals(RequestFrom.User)).collect(Collectors.toList())).orElse(null);
    }

}
