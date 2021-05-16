package pl.RK.PAIEVENTREST.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserPAI {
    @Id
    String email;


    String password;


    String nick;

    boolean activated;

    @JsonIgnore
    private String userKey;

    public UserPAI(String email, String password, String nick) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.activated = false;
        this.userKey = Hashing.sha256()
                .hashString(String.valueOf(hashCode()), StandardCharsets.UTF_8)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPAI)) return false;
        UserPAI userPAI = (UserPAI) o;
        return Objects.equals(getEmail(), userPAI.getEmail()) &&
                Objects.equals(getPassword(), userPAI.getPassword()) &&
                Objects.equals(getNick(), userPAI.getNick());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getNick(), new Date(System.currentTimeMillis()));
    }

    public void getNewKey() {
        this.userKey = Hashing.sha256()
                .hashString(String.valueOf(hashCode()), StandardCharsets.UTF_8)
                .toString();
    }


}

