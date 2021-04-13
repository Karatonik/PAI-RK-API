package pl.RK.PAIEVENTREST.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPAIDto {
    Integer eventID;

    String name;


    String province;


    String city;

    String address;

    AccessPAI access;

    LocalDateTime dateOfCreate;

    LocalDateTime dateOfStarEvent;


    public EventPAIDto(EventPAI eventPAI) {
        this.eventID = eventPAI.getEventID();
        this.name = eventPAI.getName();
        this.province=eventPAI.getProvince();
        this.city =eventPAI.getCity();
        this.address = eventPAI.getAddress();
        this.access = eventPAI.getAccess();
        this.dateOfCreate = eventPAI.getDateOfCreate();
        this.dateOfStarEvent= eventPAI.getDateOfStartEvent();
    }
}
