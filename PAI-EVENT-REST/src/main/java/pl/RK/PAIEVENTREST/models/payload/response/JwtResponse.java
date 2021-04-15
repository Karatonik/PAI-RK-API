package pl.RK.PAIEVENTREST.models.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private String nick;
    private String email;

    public JwtResponse(String token, String nick, String email) {
        this.token = token;
        this.nick = nick;
        this.email = email;

    }
}
