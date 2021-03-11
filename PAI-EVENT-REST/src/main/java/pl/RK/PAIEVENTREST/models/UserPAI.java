package pl.RK.PAIEVENTREST.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserPAI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userId;

    String login;

    String password;

    String email;

    String nick;

}
