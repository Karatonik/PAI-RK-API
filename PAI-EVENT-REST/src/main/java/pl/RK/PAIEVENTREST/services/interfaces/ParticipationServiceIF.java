package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;

import java.util.Set;

public interface ParticipationServiceIF {


    public Set<Participation> userParticipationSet(String email);

    public Set<Participation> eventParticipationSet(int eventId);


}
