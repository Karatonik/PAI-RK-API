package pl.RK.PAIEVENTREST.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserPAI {
    @Id
    @Pattern(regexp="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message ="incorrect email" )
    String email;

    @NotBlank(message ="password cannot be blank " )
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",message ="incorrect password" )
    //Password must contain at least one digit [0-9].
    // Password must contain at least one lowercase Latin character [a-z].
    //Password must contain at least one uppercase Latin character [A-Z].
    //Password must contain at least one special character like ! @ # & ( ).
    //Password must contain a length of at least 8 characters and a maximum of 20 characters.
    //Sample : !Mocnehaslo12345
    String password;

    @NotBlank(message ="nick cannot be blank " )
    String nick;

    boolean activated;

    @JsonIgnore
    private String userKey;



    public UserPAI(String email,  String password, String nick) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.activated = false;
        this.userKey = String.valueOf((hashCode()*hashCode())+21*hashCode());
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

    public void getNewKey(){
        this.userKey = String.valueOf((hashCode()*hashCode())+21*hashCode());
    }



}

