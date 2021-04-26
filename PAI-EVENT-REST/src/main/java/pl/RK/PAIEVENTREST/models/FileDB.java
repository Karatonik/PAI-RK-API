package pl.RK.PAIEVENTREST.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.RK.PAIEVENTREST.models.enums.TypeOfImage;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class FileDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer fileID;

    private String name;

    private String type;

    @Lob
    private byte[] data;



    private TypeOfImage typeOfImage;

    @OneToOne
    private UserPAI userPAI;

    @OneToOne
    private  EventPAI eventPAI;


    public FileDB(String name, String type, byte[] data, TypeOfImage typeOfImage, UserPAI userPAI) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.typeOfImage = typeOfImage;
        this.userPAI = userPAI;
    }


    public FileDB(String name, String type, byte[] data, TypeOfImage typeOfImage, UserPAI userPAI, EventPAI eventPAI) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.typeOfImage = typeOfImage;
        this.userPAI = userPAI;
        this.eventPAI = eventPAI;
    }
}
