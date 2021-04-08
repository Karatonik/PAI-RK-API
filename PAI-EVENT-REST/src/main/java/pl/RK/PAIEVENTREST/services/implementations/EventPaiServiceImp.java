package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.Comment;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.repositorys.CommentRepository;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.EventPaiServiceIF;

import java.util.*;

@Service
public class EventPaiServiceImp implements EventPaiServiceIF {

    EventPaiRepository eventPaiRepository;
    CommentRepository commentRepository;
    UserPaiRepository userPaiRepository;
    @Autowired
    public EventPaiServiceImp(EventPaiRepository eventPaiRepository,CommentRepository commentRepository
    ,UserPaiRepository userPaiRepository) {
        this.eventPaiRepository = eventPaiRepository;
        this.commentRepository=commentRepository;
        this.userPaiRepository=userPaiRepository;
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
    public EventPAI set(String name, String address, AccessPAI accessPAI, Date dateOfStartEvent, String emailOfCreator) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(emailOfCreator);
        if(optionalUserPAI.isPresent()){
            if(eventPaiRepository.findByNameAndAddress(name,address).isEmpty()){

                Set<UserPAI> userPAISet = new HashSet<>();
                userPAISet.add(optionalUserPAI.get());
             return   eventPaiRepository.save(new EventPAI(name,address ,accessPAI,dateOfStartEvent,userPAISet));
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


}
