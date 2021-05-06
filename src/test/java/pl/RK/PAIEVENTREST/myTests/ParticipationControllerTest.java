package pl.RK.PAIEVENTREST.myTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.RK.PAIEVENTREST.PaiEventRestApplication;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.Participation;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.models.enums.RequestFrom;
import pl.RK.PAIEVENTREST.services.implementations.ParticipationServiceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {PaiEventRestApplication.class})
@SpringBootTest
public class ParticipationControllerTest {
    final String  apiPath = "/api/parti";
    UserPAI user = new UserPAI("test@test.com", "!MocneHaslo1234", "Tester");
    EventPAI event = new EventPAI("testEvent", "Kujawskopomorskie", "Toruń", "Komunalna 2"
            , AccessPAI.Closed, LocalDateTime.now(), user);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParticipationServiceImp participationService;


    @Test
   public void getUserParticipationT() throws Exception{
        event.setEventID(1);
       Participation participationU= new Participation(RequestFrom.User,user,event);

        List<Participation> participationList =new ArrayList<>();
        participationList.add(participationU);

        when(participationService.userParticipationSet(anyString())).thenReturn(participationList);

        mvc.perform(get(apiPath + "/user/"+user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.com")));
    }
    @Test
    public void getEventParticipationT() throws Exception{
        event.setEventID(1);
        Participation participationE= new Participation(RequestFrom.Event,user,event);

        List<Participation> participationList =new ArrayList<>();
        participationList.add(participationE);

        when(participationService.eventParticipationSet(anyInt())).thenReturn(participationList);


        mvc.perform(get(apiPath + "/event/"+event.getEventID())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.com")));
    }
    @Test
    public void getParticipationFromEventToUserT() throws Exception{
        event.setEventID(1);
        Participation participationE= new Participation(RequestFrom.Event,user,event);

        List<Participation> participationList =new ArrayList<>();
        participationList.add(participationE);

        when(participationService.participationFromEventToUser(anyString())).thenReturn(participationList);


        mvc.perform(get(apiPath + "/user/from/event/"+user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.com")));

    }





}
