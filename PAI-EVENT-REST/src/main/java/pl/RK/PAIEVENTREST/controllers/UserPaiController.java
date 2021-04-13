package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.dto.UserPAIDto;
import pl.RK.PAIEVENTREST.services.implementations.UserPaiServiceImp;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
//http://localhost:8080/swagger-ui.html#/
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
    public UserPAIDto reset(String key){
        return new UserPAIDto(userPaiService.resetPassword(key));
    }

    //todo reset hasła vali

    @PutMapping("/delete")
    public boolean delete(String key){
        return userPaiService.deleteWithKey(key);
    }


    //rejestracja
    @PostMapping
    public UserPAIDto set(String email,String password , String nick){
        return new UserPAIDto(userPaiService.set(email,password,nick));
    }

    @PostMapping("/rtje")
    public boolean requestToJoinEvent(String email,int eventId){
        return userPaiService.requestToJoinEvent(email, eventId);
    }

    @PutMapping("/accept")
    public boolean acceptRequestToJoin(int participleId , String email){
        return userPaiService.acceptParticipation(participleId,email);
    }
    @GetMapping("/login")
    public UserPAIDto login(String email , String password){
        return new UserPAIDto(userPaiService.login(email,password));
    }
}
