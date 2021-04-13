package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.Comment;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.models.enums.RequestFrom;
import pl.RK.PAIEVENTREST.repositorys.CommentRepository;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.repositorys.ParticipationRepository;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.EventPaiServiceIF;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventPaiServiceImp implements EventPaiServiceIF {

    EventPaiRepository eventPaiRepository;
    CommentRepository commentRepository;
    UserPaiRepository userPaiRepository;
    ParticipationRepository participationRepository;
    @Autowired
    public EventPaiServiceImp(EventPaiRepository eventPaiRepository,CommentRepository commentRepository
    ,UserPaiRepository userPaiRepository,ParticipationRepository participationRepository) {
        this.eventPaiRepository = eventPaiRepository;
        this.commentRepository=commentRepository;
        this.userPaiRepository=userPaiRepository;
        this.participationRepository = participationRepository;
    }

    @Override
    public List<Comment> getAllComments(int eventId) {
      Optional<EventPAI>optionalEventPAI=   eventPaiRepository.findById(eventId);
        return optionalEventPAI.map(eventPAI -> commentRepository.findByEventPAI(eventPAI)).orElse(null);
    }

    @Override
    public Set<UserPAI> getAllUsers(int eventId) {
        Optional<EventPAI> optionalEventPAI = eventPaiRepository.findById(eventId);
        return optionalEventPAI.map(EventPAI::getUserSet).orElse(null);
    }

    @Override
    public Set<UserPAI> getAllAdmins(int eventId) {
        Optional<EventPAI> optionalEventPAI = eventPaiRepository.findById(eventId);
        return optionalEventPAI.map(EventPAI::getOrganizerSet).orElse(null);
    }

    @Override
    public List<EventPAI> get(String name, String province, String city, String address) {
        return eventPaiRepository.findByNameOrProvinceOrCityOrAddress(name, province,city,address);
    }
    @Override
    public EventPAI set(String name, String province, String city, String address, AccessPAI accessPAI, LocalDateTime dateOfStartEvent, String emailOfCreator) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(emailOfCreator);
        if(optionalUserPAI.isPresent()){
            if(eventPaiRepository.findByNameAndProvinceAndCityAndAddress(name,province,city,address).isEmpty()){

                Set<UserPAI> userPAISet = new HashSet<>();
                userPAISet.add(optionalUserPAI.get());
                return   eventPaiRepository.save(new EventPAI(name,province,city,address ,accessPAI,dateOfStartEvent,userPAISet));
            }
            return  null;
        }
        return null;
    }


    @Override
    public boolean delete(int eventId) {
        Optional<EventPAI> optionalEventPAI= eventPaiRepository.findById(eventId);
        if(optionalEventPAI.isPresent()){
            eventPaiRepository.delete(optionalEventPAI.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean changeAccess(int eventId) {
        Optional<EventPAI>optionalEventPAI= eventPaiRepository.findById(eventId);
        if(optionalEventPAI.isPresent()){
           EventPAI eventPAI = optionalEventPAI.get();
           if(eventPAI.getAccess().equals(AccessPAI.Closed)){
               eventPAI.setAccess(AccessPAI.Open);
           }else {
               eventPAI.setAccess(AccessPAI.Closed);
           }
           eventPaiRepository.save(eventPAI);
           return true;
        }
        return false;
    }

    @Override
    public boolean addUser(String email , int eventId) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        Optional<EventPAI> optionalEventPAI= eventPaiRepository.findById(eventId);
        if(optionalEventPAI.isPresent()&&optionalUserPAI.isPresent()){
                EventPAI eventPAI = optionalEventPAI.get();
                UserPAI userPAI = optionalUserPAI.get();

                //sprawdzam czy nie ma juz takiego zaproszenia
                if(!participationRepository.findByUserPAIAndEventPAI(userPAI,eventPAI).isPresent()){
                    //sprawdzam czy nie ma takiego użytkownika w wydarzeniu
                    if(!(eventPAI.getUserSet().contains(userPAI))||!(eventPAI.getOrganizerSet().contains(userPAI))){

                        participationRepository.save(new Participation(RequestFrom.Event,userPAI,eventPAI));
                        return true;
                    }
                }
        }

        return false;
    }

    @Override
    public boolean acceptParticipation(int participationId , int eventId) {
        Optional<Participation>optionalParticipation =participationRepository.findById(participationId);
        if(optionalParticipation.isPresent()){
            Participation participation = optionalParticipation.get();

            if(participation.getRequest().equals(RequestFrom.User)){
                //sprawdzam bo konto lub wydarzenie  mogło zostac usunięte
                if (userPaiRepository.findById(participation.getUserPAI().getEmail()).isPresent()
                        &&eventPaiRepository.findById(participation.getEventPAI().getEventID()).isPresent()){
                    EventPAI eventPAI = participation.getEventPAI();
                        //na koniec sprawdzam kontrolnie czy z wydarzenie się gadza
                    if(eventPAI.getEventID()==eventId) {
                        Set<UserPAI> userPAISet = eventPAI.getUserSet();
                        userPAISet.add(participation.getUserPAI());
                        eventPAI.setUserSet(userPAISet);
                        eventPaiRepository.save(eventPAI);
                        participationRepository.delete(participation);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean setUserAsAdmin(String email, int eventId) {
        Optional<EventPAI> optionalEventPAI =eventPaiRepository.findById(eventId);
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        if(optionalEventPAI.isPresent() &&optionalUserPAI.isPresent()){
          UserPAI userPAI = optionalUserPAI.get();
          EventPAI eventPAI=  optionalEventPAI.get();
          Set<UserPAI>users = eventPAI.getUserSet();
          if(users.contains(userPAI)){
              Set<UserPAI> admins=eventPAI.getOrganizerSet();
              admins.add(userPAI);
              users.remove(userPAI);
              eventPAI.setOrganizerSet(admins);
              eventPAI.setUserSet(users);
              eventPaiRepository.save(eventPAI);
              return true;
          }
          return  false;
        }
        return false;
    }


}
