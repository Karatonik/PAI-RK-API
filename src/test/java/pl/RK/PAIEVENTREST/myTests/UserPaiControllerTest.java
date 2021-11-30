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
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.services.implementations.UserPaiServiceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {PaiEventRestApplication.class})
@SpringBootTest
public class UserPaiControllerTest {
    final String  apiPath = "/api/user";
    UserPAI user = new UserPAI("test@test.com", "!MocneHaslo1234", "Tester");
    EventPAI event = new EventPAI("testEvent", "Kujawskopomorskie", "Toruń", "Komunalna 2"
            , AccessPAI.Closed, LocalDateTime.now(), user);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserPaiServiceImp userPaiService;

    //@Test
    public void confirmTest() throws Exception{
        when(userPaiService.confirmation(anyString())).thenReturn(true);

        mvc.perform(put(apiPath + "/confirmation/myKey")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }
    @Test
    public void resetPWDTest() throws Exception{
        when(userPaiService.changePassword(anyString(),anyString())).thenReturn(true);
        mvc.perform(put(apiPath + "/pwd/myKey/"+user.getPassword())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }
   // @Test
    public void deleteTest() throws  Exception{
    when(userPaiService.deleteWithKey(anyString())).thenReturn(true);
        mvc.perform(delete(apiPath + "/delete/myKey")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public  void createTest() throws  Exception{
    when(userPaiService.set(anyString(),anyString(),anyString())).thenReturn(user);

        mvc.perform(post(apiPath + "/"+user.getEmail()+"/"+user.getPassword()+"/"+user.getNick())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.com")));
    }

//    @Test
//    public void rtjeTest() throws Exception{
//    when(userPaiService.requestToJoinEvent(anyString(),anyInt())).thenReturn(true);
//        mvc.perform(post(apiPath + "/rtje/"+user.getEmail()+"/1")
//                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("true")));
//    }

    @Test
    public void acceptRequestToJoinTest() throws Exception{
    when(userPaiService.acceptParticipation(anyInt(),anyString())).thenReturn(true);
        mvc.perform(put(apiPath + "/accept/1/"+user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }



    @Test
    public void getTest() throws Exception{
    when(userPaiService.get(anyString())).thenReturn(user);
        mvc.perform(get(apiPath + "/"+user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.com")));
    }
    @Test
    public void getAllMyEventWhereIMUserTest() throws Exception{
        event.setEventID(1);
        List<EventPAI> eventPAIList = new ArrayList<>();
        eventPAIList.add(event);
       when(userPaiService.getAllMyEventWhereIMUser(anyString())).thenReturn(eventPAIList);

        mvc.perform(get(apiPath + "/events/user/"+user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
               .andExpect(content().string(containsString("testEvent")));
    }
    @Test
    public void getAllMyEventWhereIMAdminTest() throws Exception{
        event.setEventID(1);
        List<EventPAI> eventPAIList = new ArrayList<>();
        eventPAIList.add(event);
        when(userPaiService.getAllMyEventWhereIMAdmin(anyString())).thenReturn(eventPAIList);

        mvc.perform(get(apiPath + "/events/admin/"+user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEvent")));
    }




}
