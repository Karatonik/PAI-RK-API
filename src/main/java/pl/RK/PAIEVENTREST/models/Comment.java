package pl.RK.PAIEVENTREST.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer commentId;

    String text;

    @CreationTimestamp
    Date date;
    @OneToOne
    EventPAI eventPAI;

    @OneToOne
    UserPAI userPai;


    public Comment(String text, EventPAI eventPAI, UserPAI userPai) {
        this.text = text;
        this.eventPAI = eventPAI;
        this.userPai = userPai;
    }
}
