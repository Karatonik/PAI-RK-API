package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.RK.PAIEVENTREST.services.implementations.ParticipationServiceImp;

@RestController("/parti")
public class ParticipationController {

    ParticipationServiceImp participationService;
    @Autowired
    public ParticipationController(ParticipationServiceImp participationService) {
        this.participationService = participationService;
    }
}
