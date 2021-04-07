package pl.RK.PAIEVENTREST.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class EventPAI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long eventID;

    String name;

    String address;

    AccessPAI access;

    Date dateOfCreate;

    Date dateOfStarEvent;

    @OneToMany
    Set<UserPAI> Organizer;

    @OneToMany
    Set<UserPAI> userSet;

}
