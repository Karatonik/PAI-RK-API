package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.Comment;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface EventPaiServiceIF {


    public List<Comment> getAllComments(int eventId);

    public Set<UserPAI> getAllUsers(int eventId);

    public Set<UserPAI> getAllAdmins(int eventId);

    public EventPAI  set(String name, String address , AccessPAI accessPAI , Date dateOfStartEvent , String emailOfCreator);

    public boolean delete(int eventId);
}
