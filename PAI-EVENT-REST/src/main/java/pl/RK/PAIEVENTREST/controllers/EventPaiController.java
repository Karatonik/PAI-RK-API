package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.dto.CommentDto;
import pl.RK.PAIEVENTREST.models.dto.EventPAIDto;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.services.implementations.EventPaiServiceImp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController("/api/event")
public class EventPaiController {

    EventPaiServiceImp eventPaiService;

    @Autowired
    public EventPaiController(EventPaiServiceImp eventPaiService) {
        this.eventPaiService = eventPaiService;
    }

    @GetMapping("/Comments")
    //bez danych wrażliwych
    public List<CommentDto> getAllComments(int eventId){
        return eventPaiService.getAllComments(eventId).stream().map(CommentDto::new).collect(Collectors.toList());
    }
    @PutMapping
    public EventPAIDto addEvent(String name,@RequestBody String address , @RequestBody AccessPAI accessPAI
            , @RequestBody Date dateOfStartEvent ,@RequestBody String email){
        return new EventPAIDto( eventPaiService.set(name,address,accessPAI,dateOfStartEvent,email));
    }

    @DeleteMapping
    public boolean deleteEvent(int eventId){
        return  eventPaiService.delete(eventId);
    }

}
