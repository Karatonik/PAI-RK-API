package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.dto.EventPAIDto;
import pl.RK.PAIEVENTREST.models.dto.UserPAIDto;
import pl.RK.PAIEVENTREST.services.implementations.UserPaiServiceImp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserPaiController {


    UserPaiServiceImp userPaiService;

    @Autowired
    public UserPaiController(UserPaiServiceImp userPaiService) {
        this.userPaiService = userPaiService;
    }

    @GetMapping("/confirmation/{key}")
    public String confirmation(@PathVariable @Size(min = 20) String key) {
        if (userPaiService.confirmation(key)) {
            return "Konto uzuyskało weryfikację";
        } else {
            return "Błąd serwisu";
        }
    }

    //zmiana hasła , potrzebny klucz z maila
    @PutMapping("/pwd/{key}/{pwd}")
    public Boolean resetPwd(@PathVariable @Size(min = 20) String key
            , @PathVariable @Size(min = 8, max = 20) String pwd) {
        return userPaiService.changePassword(key, pwd);
    }


    @GetMapping("/delete/{key}")
    public String delete(@PathVariable @Size(min = 20) String key) {

        if (userPaiService.deleteWithKey(key)) {
            return "Konto zostało usunięte";
        } else {
            return "Błąd serwisu";
        }
    }

    @PostMapping("{email}/{password}/{nick}")
    public UserPAIDto set(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                  String email,
                          @PathVariable @Size(min = 8, max = 20) String password, @PathVariable @NotBlank String nick) {
        return new UserPAIDto(userPaiService.set(email, password, nick));
    }


    @PostMapping("/rtje/{email}/{eventId}")
    public Participation requestToJoinEvent(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                                    String email,
                                            @PathVariable @NotBlank int eventId) {
        return userPaiService.requestToJoinEvent(email, eventId);
    }

    @PutMapping("/accept/{participleId}/{email}")
    public boolean acceptRequestToJoin(@PathVariable @NotBlank int participleId
            , @PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                               String email) {
        return userPaiService.acceptParticipation(participleId, email);
    }


    @GetMapping("/{email}")
    public UserPAIDto get(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                  String email) {
        return new UserPAIDto(userPaiService.get(email));
    }


    @GetMapping("/events/user/{email}")
    public List<EventPAIDto> getAllMyEventWhereIMUser(@PathVariable
                                                      @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                                              String email) {
        return userPaiService.getAllMyEventWhereIMUser(email).stream()
                .parallel().map(EventPAIDto::new).collect(Collectors.toList());
    }

    @GetMapping("/events/admin/{email}")
    public List<EventPAIDto> getAllMyEventWhereIMAdmin(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                                               String email) {
        return userPaiService.getAllMyEventWhereIMAdmin(email).stream()
                .parallel().map(EventPAIDto::new).collect(Collectors.toList());
    }

}
