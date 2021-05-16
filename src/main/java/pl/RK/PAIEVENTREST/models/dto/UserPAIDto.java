package pl.RK.PAIEVENTREST.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.RK.PAIEVENTREST.models.UserPAI;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPAIDto {


    String email;

    String nick;

    boolean activated;

    public UserPAIDto(UserPAI userPAI) {
        this.email = userPAI.getEmail();
        this.nick = userPAI.getNick();
        this.activated=userPAI.isActivated();
    }
}
