package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.Comment;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventPaiServiceIF {


    List<Comment> getAllComments(int eventId);

    Set<UserPAI> getAllUsers(int eventId);

    Set<UserPAI> getAllAdmins(int eventId);

    EventPAI get(int eventId);

    List<EventPAI> getAll();

    List<EventPAI> getAllByCity(String city);

    EventPAI set(String name, String province, String city, String address, AccessPAI accessPAI, LocalDateTime dateOfStartEvent, String emailOfCreator);

    boolean delete(int eventId);

    boolean changeAccess(int eventId);

    boolean addUser(String email, int eventId);

    boolean acceptParticipation(int participationId, int eventId);

    boolean setUserAsAdmin(String email, int eventId);

    boolean setGeoLocal(int eventId ,double x,double y);
}
