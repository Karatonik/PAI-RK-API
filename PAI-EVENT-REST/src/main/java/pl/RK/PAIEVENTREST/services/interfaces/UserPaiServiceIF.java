package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.UserPAI;

public interface UserPaiServiceIF {


    public boolean confirmation(String key);

    public UserPAI resetPassword(String key);

    public boolean deleteWithKey(String key);


}
