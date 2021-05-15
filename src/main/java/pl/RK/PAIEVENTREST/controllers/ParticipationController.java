package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.dto.ParticipationDto;
import pl.RK.PAIEVENTREST.services.implementations.ParticipationServiceImp;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parti")
@CrossOrigin(origins = "http://localhost:3000")
public class ParticipationController {

    ParticipationServiceImp participationService;

    @Autowired
    public ParticipationController(@PathVariable ParticipationServiceImp participationService) {
        this.participationService = participationService;
    }

    @GetMapping("/user/{email}")
    public List<ParticipationDto> getUserParticipation(@PathVariable String email) {
        return participationService.userParticipationSet(email).stream().map(ParticipationDto::new).collect(Collectors.toList());
    }

    @GetMapping("/event/{eventId}")
    public List<ParticipationDto> getEventParticipation(@PathVariable int eventId) {
        return participationService.eventParticipationSet(eventId).stream().map(ParticipationDto::new).collect(Collectors.toList());
    }

    @GetMapping("/user/from/event/{email}")
    public List<ParticipationDto> getParticipationFromEventToUser(@PathVariable String email) {
        return participationService.participationFromEventToUser(email)
                .stream().map(ParticipationDto::new).collect(Collectors.toList());
    }

    @GetMapping("/event/from/user/{eventId}")
    public List<ParticipationDto> getParticipationFromUserToEvent(@PathVariable int eventId) {
        return participationService.participationFromUserToEvent(eventId).stream().map(ParticipationDto::new).collect(Collectors.toList());
    }


}
