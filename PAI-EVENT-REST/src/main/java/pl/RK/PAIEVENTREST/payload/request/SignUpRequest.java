package pl.RK.PAIEVENTREST.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;



@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank
    private  String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nick;

    boolean activated;


    public SignUpRequest(@NotBlank String email, @NotBlank String password, @NotBlank String nick) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.activated=false;
    }
}
