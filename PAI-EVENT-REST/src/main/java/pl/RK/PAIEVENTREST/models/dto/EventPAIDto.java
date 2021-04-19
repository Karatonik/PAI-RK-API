package pl.RK.PAIEVENTREST.models.dto;

import lombok.*;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventPAIDto {
    Integer eventID;

    String name;


    String province;


    String city;

    String address;

    AccessPAI access;

    LocalDateTime dateOfCreate;

    LocalDateTime dateOfStarEvent;

    double x;

    double y;


    public EventPAIDto(EventPAI eventPAI) {
        this.eventID = eventPAI.getEventID();
        this.name = eventPAI.getName();
        this.province=eventPAI.getProvince();
        this.city =eventPAI.getCity();
        this.address = eventPAI.getAddress();
        this.access = eventPAI.getAccess();
        this.dateOfCreate = eventPAI.getDateOfCreate();
        this.dateOfStarEvent= eventPAI.getDateOfStartEvent();
        //geo
        this.x=eventPAI.getX();
        this.y =eventPAI.getY();
    }
}
