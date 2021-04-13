package pl.RK.PAIEVENTREST.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    String province;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String address;

    AccessPAI access;

    @CreationTimestamp
    LocalDateTime dateOfCreate;

    @Column(nullable = false)
    LocalDateTime dateOfStartEvent;

    @OneToMany
    @JoinColumn(name ="userpai")
    Set<UserPAI> OrganizerSet;

    @OneToMany
    @JoinColumn(name ="userpai")
    Set<UserPAI> userSet;


    public EventPAI(String name, String province, String city, String address, AccessPAI access, LocalDateTime dateOfStartEvent, Set<UserPAI> userSet) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.address = address;
        this.access = access;
        this.dateOfStartEvent = dateOfStartEvent;
        this.userSet = userSet;
    }


}
