package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.dto.CommentDto;
import pl.RK.PAIEVENTREST.models.dto.EventPAIDto;
import pl.RK.PAIEVENTREST.models.dto.UserPAIDto;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.services.implementations.EventPaiServiceImp;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
public class EventPaiController {

    EventPaiServiceImp eventPaiService;

    @Autowired
    public EventPaiController(EventPaiServiceImp eventPaiService) {
        this.eventPaiService = eventPaiService;
    }

    @GetMapping("/comments")
    public List<CommentDto> getAllComments(int eventId){
        return eventPaiService.getAllComments(eventId).stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @PostMapping
    public EventPAIDto addEvent(String name,@RequestBody String prov ,@RequestBody String city ,@RequestBody String address , @RequestBody AccessPAI accessPAI
            , @RequestBody Date dateOfStartEvent ,@RequestBody String email){
        return new EventPAIDto( eventPaiService.set(name,prov,city,address,accessPAI,dateOfStartEvent,email));
    }

    @DeleteMapping
    public boolean deleteEvent(int eventId){
        return  eventPaiService.delete(eventId);
    }

    @GetMapping
    public List<EventPAIDto> get(String name,@RequestBody String prov ,@RequestBody String city ,@RequestBody String address){
        return eventPaiService.get(name,prov,city,address).stream().map(EventPAIDto::new).collect(Collectors.toList());
    }

    @GetMapping("/users")
    public Set<UserPAIDto>getAllUsers(int eventId){
        return eventPaiService.getAllUsers(eventId).stream().map(UserPAIDto::new).collect(Collectors.toSet());
    }

    @GetMapping("/admins")
    public Set<UserPAIDto>getAllAdmins(int eventId){
        return eventPaiService.getAllAdmins(eventId).stream().map(UserPAIDto::new).collect(Collectors.toSet());
    }

    @PutMapping("/changeAccess")
    public boolean changeAccess(int eventId){
        return eventPaiService.changeAccess(eventId);
    }

    @PutMapping("/setAdmin")
    public boolean setUserAsAdmin(String email , int eventId){
        return eventPaiService.setUserAsAdmin(email,eventId);
    }

    //dodanie do zapro
    @PostMapping("/addUser")
    public boolean addUser(String email,int eventId){
        return eventPaiService.addUser(email,eventId);
    }

    //akceptacja prośby o dołączenie
    @PutMapping("/acceptPart")
    public boolean acceptParticipation(int participationId , int eventId){
        return  eventPaiService.acceptParticipation(participationId,eventId);
    }
}
