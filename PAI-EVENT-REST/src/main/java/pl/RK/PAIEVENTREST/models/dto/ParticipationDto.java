package pl.RK.PAIEVENTREST.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.enums.RequestFrom;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDto {

    Integer participationId;


    RequestFrom request;


    UserPAIDto userPAIDto;


    EventPAIDto eventPAIDto;


    public ParticipationDto(Participation participation) {
        this.participationId = participation.getParticipationId();
        this.request = participation.getRequest();
        this.userPAIDto = new UserPAIDto(participation.getUserPAI());
        this.eventPAIDto = new EventPAIDto(participation.getEventPAI());
    }
}
