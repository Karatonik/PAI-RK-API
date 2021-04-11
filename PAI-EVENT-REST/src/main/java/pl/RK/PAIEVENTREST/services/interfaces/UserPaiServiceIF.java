package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.UserPAI;

public interface UserPaiServiceIF {


    public boolean confirmation(String key);

    public UserPAI resetPassword(String key);

    public boolean deleteWithKey(String key);

    public UserPAI set(String email,String password ,String nick);

    public boolean requestToJoinEvent(String email,int eventId);

    public boolean acceptParticipation(int participationId , String email);

    public UserPAI login(String email , String password);


}
