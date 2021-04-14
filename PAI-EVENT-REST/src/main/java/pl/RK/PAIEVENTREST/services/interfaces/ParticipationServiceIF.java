package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.Participation;

import java.util.Set;

public interface ParticipationServiceIF {


    Set<Participation> userParticipationSet(String email);

    Set<Participation> eventParticipationSet(int eventId);


}
