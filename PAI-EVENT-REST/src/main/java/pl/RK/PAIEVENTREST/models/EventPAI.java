package pl.RK.PAIEVENTREST.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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
    Integer eventID;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String address;

    AccessPAI access;

    @CreationTimestamp
    Date dateOfCreate;

    @Column(nullable = false)
    Date dateOfStarEvent;

    @OneToMany
    @JoinColumn(name ="userpai")
    Set<UserPAI> OrganizerSet;

    @OneToMany
    @JoinColumn(name ="userpai")
    Set<UserPAI> userSet;

    public EventPAI(String name, String address, AccessPAI access, Date dateOfStarEvent, Set<UserPAI> organizer) {
        this.name = name;
        this.address = address;
        this.access = access;
        this.dateOfStarEvent = dateOfStarEvent;
        OrganizerSet = organizer;
    }
}
