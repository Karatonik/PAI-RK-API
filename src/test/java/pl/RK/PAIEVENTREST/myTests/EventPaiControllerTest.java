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
import pl.RK.PAIEVENTREST.models.Comment;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.AccessPAI;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.services.implementations.CommentServiceImp;
import pl.RK.PAIEVENTREST.services.implementations.EventPaiServiceImp;
import pl.RK.PAIEVENTREST.services.implementations.ParticipationServiceImp;
import pl.RK.PAIEVENTREST.services.implementations.UserPaiServiceImp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {PaiEventRestApplication.class})
@SpringBootTest
public class EventPaiControllerTest {

    private final String apiPath = "/api/event";


    public UserPAI user = new UserPAI("test@test.com", "!MocneHaslo1234", "Tester");
    public UserPAI user2 = new UserPAI("test2@test.com", "!MocneHaslo1234", "Tester");
    public EventPAI event = new EventPAI("testEvent", "Kujawskopomorskie", "Toruń", "Komunalna 2"
            , AccessPAI.Closed, LocalDateTime.now(), user);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserPaiServiceImp userPaiService;

    @MockBean
    private EventPaiServiceImp eventPaiService;

    @Autowired
    private CommentServiceImp commentService;

    @Autowired
    private ParticipationServiceImp participationService;


    @Autowired
    private EventPaiRepository eventPaiRepository;

    @Test
    public void setEvent() throws Exception {
        user = userPaiService.set(user.getEmail(), user.getPassword(), user.getNick());
        this.event.setEventID(1);

        when(eventPaiService.set(any(String.class), any(String.class), any(String.class), any(String.class)
                , any(AccessPAI.class), any(LocalDateTime.class), any(String.class))).thenReturn(event);

        mvc.perform(post(apiPath + "/" + event.getName() + "/" + event.getProvince() + "/"
                + event.getCity() + "/" + event.getAddress() + "/" + event.getAccess() + "/"
                + event.getDateOfStartEvent().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                + "/" + user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEvent")));
    }

    @Test
    public void getEvent() throws Exception {
        this.event.setEventID(1);
        when(eventPaiService.get(1))
                .thenReturn(event);

        mvc.perform(get(apiPath + "/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEvent")));

    }


    @Test
    public void getAllComments() throws Exception {
        this.event.setEventID(1);
        Comment comment = new Comment(1, "rerwerwerwer", new Date(System.currentTimeMillis()), event, user);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        when(eventPaiService.getAllComments(1)).thenReturn(comments);

        mvc.perform(get(apiPath + "/comments/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("rerwerwerwer")));

    }

    @Test
    public void getAllEvent() throws Exception {
        this.event.setEventID(1);
        List<EventPAI> eventPAIList = new ArrayList<>();
        eventPAIList.add(event);
        when(eventPaiService.getAll()).thenReturn(eventPAIList);

        mvc.perform(get(apiPath)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEvent")));

    }


    @Test
    public void addUser() throws Exception {

        when(eventPaiService.addUser(user2.getEmail(), 1)).thenReturn(true);

        mvc.perform(post(apiPath + "/addUser/" + user2.getEmail() + "/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void acceptParticipation() throws Exception {
        when(eventPaiService.acceptParticipation(1, 1)).thenReturn(true);

        mvc.perform(put(apiPath + "/acceptPart/1/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void getAllUsers() throws Exception {
        Set<UserPAI> userPAIS = new HashSet<>();
        userPAIS.add(user2);


        when(eventPaiService.getAllUsers(1)).thenReturn(userPAIS);

        mvc.perform(get(apiPath + "/users/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test2@test.com")));
    }


    @Test
    public void changeAccess() throws Exception {
        when(eventPaiService.changeAccess(1)).thenReturn(true);

        mvc.perform(put(apiPath + "/changeAccess/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }


    @Test
    public void setUserAdmin() throws Exception {
        when(eventPaiService.setUserAsAdmin(user2.getEmail(), 1)).thenReturn(true);

        mvc.perform(put(apiPath + "/setAdmin/" + user2.getEmail() + "/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void getAllAdmins() throws Exception {
        Set<UserPAI> userPAIS = new HashSet<>();
        userPAIS.add(user2);
        when(eventPaiService.getAllAdmins(anyInt())).thenReturn(userPAIS);


        mvc.perform(get(apiPath + "/admins/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test2@test.com")));
    }

    @Test
    public void deleteEvent() throws Exception {
        when(eventPaiService.delete(1)).thenReturn(true);

        mvc.perform(delete(apiPath + "/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void setGeoEvent() throws Exception {
        when(eventPaiService.setGeoLocal(anyInt(), anyDouble(), anyDouble())).thenReturn(true);
        double x = 12.3;
        double y = 8.12;

        mvc.perform(put(apiPath + "/geo/1/" + x + "/" + y)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void findByCityEvents() throws Exception {
        List<EventPAI> eventPAIList = new ArrayList<>();
        eventPAIList.add(event);


        when(eventPaiService.getAllByCity(anyString())).thenReturn(eventPAIList);

        mvc.perform(get(apiPath + "/city/" + event.getCity())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEvent")));


    }
    @Test
    public void findAllInRangeTest() throws Exception{
        EventPAI eventPAI1 = new EventPAI("testEvent22", "Kujawskopomorskie", "Toruń", "Komunalna 2"
                , AccessPAI.Closed, LocalDateTime.now(), user);
        List<EventPAI> eventPAIList = new ArrayList<>();
        eventPAIList.add(event);
        eventPAIList.add(eventPAI1);

        when(eventPaiService.getAllInRange(anyInt(),anyDouble())).thenReturn(eventPAIList);

        mvc.perform(get(apiPath + "/geo/1/r/20")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEvent")));

    }
    @Test
    public void findAllInRangeByGeoTest() throws Exception{
        EventPAI eventPAI1 = new EventPAI("testEvent22", "Kujawskopomorskie", "Toruń", "Komunalna 2"
                , AccessPAI.Closed, LocalDateTime.now(), user);
        List<EventPAI> eventPAIList = new ArrayList<>();
        eventPAIList.add(event);
        eventPAIList.add(eventPAI1);

        when(eventPaiService.getAllInRangeByGeoLocation(anyDouble(),anyDouble(),anyDouble())).thenReturn(eventPAIList);

        mvc.perform(get(apiPath + "/geoXY/0/0/20")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testEvent")));

    }




}
