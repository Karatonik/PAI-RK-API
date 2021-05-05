package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.Participation;

import java.util.List;
import java.util.Set;

public interface ParticipationServiceIF {


    List<Participation> userParticipationSet(String email);

    List<Participation> eventParticipationSet(int eventId);


}
