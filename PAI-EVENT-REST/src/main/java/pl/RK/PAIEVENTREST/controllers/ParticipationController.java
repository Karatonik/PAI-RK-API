package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.RK.PAIEVENTREST.models.dto.ParticipationDto;
import pl.RK.PAIEVENTREST.services.implementations.ParticipationServiceImp;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parti")
public class ParticipationController {

    ParticipationServiceImp participationService;
    @Autowired
    public ParticipationController(ParticipationServiceImp participationService) {
        this.participationService = participationService;
    }

    @GetMapping("/user")
    public Set<ParticipationDto> getUserParticipation(String email){
        return participationService.userParticipationSet(email).stream().map(ParticipationDto::new).collect(Collectors.toSet());
    }

    @GetMapping("/event")
    public Set<ParticipationDto>getEventParticipation(int eventId){
        return participationService.eventParticipationSet(eventId).stream().map(ParticipationDto::new).collect(Collectors.toSet());
    }
}
