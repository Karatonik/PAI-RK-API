package pl.RK.PAIEVENTREST.models.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    boolean activated;
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
    private String email;
    @Size(min = 8, max = 20)
    private String password;
    @NotBlank
    private String nick;


    public SignUpRequest(String email, String password, String nick) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.activated = false;
    }
}
