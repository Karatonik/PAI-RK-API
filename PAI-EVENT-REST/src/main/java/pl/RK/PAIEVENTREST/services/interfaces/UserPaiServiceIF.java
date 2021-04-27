package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;

import java.util.List;

public interface UserPaiServiceIF {


    boolean confirmation(String key);


    boolean changePassword(String key , String newPassword);

    boolean deleteWithKey(String key);

    UserPAI set(String email, String password, String nick);

    Participation requestToJoinEvent(String email, int eventId);

    boolean acceptParticipation(int participationId, String email);

    UserPAI get(String email);


    List<EventPAI> getAllMyEventWhereIMAdmin(String email);
    List<EventPAI> getAllMyEventWhereIMUser (String email);


}
