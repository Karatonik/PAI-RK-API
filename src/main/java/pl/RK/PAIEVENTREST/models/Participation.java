package pl.RK.PAIEVENTREST.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.RK.PAIEVENTREST.models.enums.RequestFrom;

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


    RequestFrom request;

   @OneToOne
    UserPAI userPAI;

    @OneToOne
    EventPAI eventPAI;

    public Participation(RequestFrom request, UserPAI userPAI, EventPAI eventPAI) {
        this.request = request;
        this.userPAI = userPAI;
        this.eventPAI = eventPAI;
    }
}
