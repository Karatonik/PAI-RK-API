package pl.RK.PAIEVENTREST.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
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

    //geo x
    @Column(columnDefinition = "double default 0")
    double x;
    //geo y
    @Column(columnDefinition = "double default 0")
    double y;
    // @OneToMany//(mappedBy ="userpai")
    // @JoinColumn(name ="userpai")
    @ManyToMany(cascade = CascadeType.ALL)
    Set<UserPAI> organizerSet;

    // @OneToMany//(mappedBy ="userpai")
    // @JoinColumn(name ="userpai")
    @ManyToMany(cascade = CascadeType.ALL)
    Set<UserPAI> userSet;


    public EventPAI(String name, String province, String city, String address, AccessPAI access, LocalDateTime dateOfStartEvent, Set<UserPAI> organizerSet) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.address = address;
        this.access = access;
        this.dateOfStartEvent = dateOfStartEvent;
        this.userSet = new HashSet<>();
        this.organizerSet = organizerSet;
        this.x = 0;
        this.y = 0;
    }

    public EventPAI(String name, String province, String city, String address, AccessPAI access, LocalDateTime dateOfStartEvent, UserPAI organizer) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.address = address;
        this.access = access;
        this.dateOfStartEvent = dateOfStartEvent;
        this.userSet = new HashSet<>();
        this.organizerSet = new HashSet<>();
        this.organizerSet.add(organizer);
        this.x = 0;
        this.y = 0;
    }


}
