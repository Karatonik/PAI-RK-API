package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.dto.EventPAIDto;
import pl.RK.PAIEVENTREST.models.dto.UserPAIDto;
import pl.RK.PAIEVENTREST.services.implementations.UserPaiServiceImp;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Collectors;

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

    @PutMapping("/confirmation/{key}")
    public boolean confirmation(@PathVariable String key){
        return userPaiService.confirmation(key);
    }

    //zmiana hasła , potrzebny klucz z maila
    @PutMapping("/pwd/{key}/{pwd}")
    public Boolean resetPwd(@PathVariable String key,@PathVariable String pwd){
        return userPaiService.changePassword(key, pwd);
    }



    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable String key){
        return userPaiService.deleteWithKey(key);
    }


    //rejestracja
    @PostMapping("{email}/{password}/{nick}")
    public UserPAIDto set(@PathVariable String email,@PathVariable String password ,@PathVariable String nick){
        return new UserPAIDto(userPaiService.set(email,password,nick));
    }

    @PostMapping("/rtje/{email}/{eventId}")
    public boolean requestToJoinEvent(@PathVariable String email,@PathVariable int eventId){
        return userPaiService.requestToJoinEvent(email, eventId);
    }

    @PutMapping("/accept/{participleId}/{email}")
    public boolean acceptRequestToJoin(@PathVariable int participleId ,@PathVariable String email){
        return userPaiService.acceptParticipation(participleId,email);
    }
    @GetMapping("/{email}")
    public UserPAIDto get(@PathVariable String email){
        return new UserPAIDto(userPaiService.get(email));
    }


    @GetMapping("/events/user/{email}")
    public List<EventPAIDto> getAllMyEventWhereIMUser(@PathVariable String email){
        return userPaiService.getAllMyEventWhereIMUser(email).stream()
                .parallel().map(EventPAIDto::new).collect(Collectors.toList());
    }
    @GetMapping("/events/admin/{email}")
    public List<EventPAIDto>getAllMyEventWhereIMAdmin(@PathVariable String email){
        return userPaiService.getAllMyEventWhereIMAdmin(email).stream()
                .parallel().map(EventPAIDto::new).collect(Collectors.toList());
    }

}
