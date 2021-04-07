package pl.RK.PAIEVENTREST.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long commentId;

    String text;

    Date date;
    @OneToOne
    EventPAI eventPAI;

    @OneToOne
    UserPAI userPai;
}
