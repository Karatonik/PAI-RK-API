package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.services.implementations.UserPaiServiceImp;

@RestController("/api/user")
public class UserPaiController {


    UserPaiServiceImp userPaiService;
    @Autowired
    public UserPaiController(UserPaiServiceImp userPaiService) {
        this.userPaiService = userPaiService;
    }

    @PutMapping("/confirmation")
    public boolean confirmation(String key){
        return userPaiService.confirmation(key);
    }

    @PutMapping("/reset")
    public UserPAI reset(String key){
        return userPaiService.resetPassword(key);
    }

    @PutMapping("/delete")
    public boolean delete(String key){
        return userPaiService.deleteWithKey(key);
    }


}
