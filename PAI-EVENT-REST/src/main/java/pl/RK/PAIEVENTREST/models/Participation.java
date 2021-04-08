package pl.RK.PAIEVENTREST.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer participationId;

    boolean approved;

   @OneToOne
    UserPAI userPAI;

    @OneToOne
    EventPAI eventPAI;

}
