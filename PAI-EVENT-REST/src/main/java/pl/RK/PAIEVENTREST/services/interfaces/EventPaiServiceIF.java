package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.Comment;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface EventPaiServiceIF {


    public List<Comment> getAllComments(int eventId);

    public Set<UserPAI> getAllUsers(int eventId);

    public Set<UserPAI> getAllAdmins(int eventId);

    public List<EventPAI> get(String name,String province, String city, String address);

    public EventPAI  set(String name, String province, String city, String address , AccessPAI accessPAI , LocalDateTime dateOfStartEvent , String emailOfCreator);

    public boolean delete(int eventId);

    public boolean changeAccess(int eventId);

    public boolean addUser(String email,int eventId);

    public boolean acceptParticipation(int participationId ,int eventId);

    public boolean setUserAsAdmin(String email, int eventId);
}
