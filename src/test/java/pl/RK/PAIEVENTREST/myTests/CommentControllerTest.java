package pl.RK.PAIEVENTREST.myTests;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pl.RK.PAIEVENTREST.services.implementations.CommentServiceImp;

import java.time.LocalDateTime;
import java.util.Date;

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
public class CommentControllerTest {
    private final String apiPath = "/api/comm";
    UserPAI user = new UserPAI("test@test.com", "!MocneHaslo1234", "Tester");
    EventPAI event = new EventPAI("testEvent", "Kujawskopomorskie", "Toruń", "Komunalna 2"
            , AccessPAI.Closed, LocalDateTime.now(), user);
    Comment comment = new Comment("blablabal", event, user);
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentServiceImp commentService;

    @Test //ok
    public void create() throws Exception {
        event.setEventID(1);
        event.setDateOfCreate(LocalDateTime.now());


        comment.setCommentId(1);
        comment.setDate(new Date(System.currentTimeMillis()));

        when(commentService.create(anyString(), anyInt(), anyString())).thenReturn(comment);


        mvc.perform(post(apiPath + "/" + user.getEmail() + "/" + comment.getCommentId()+"/"+comment.getText())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.com")));

    }

    @Test   //ok
    public void update() throws Exception {
        event.setEventID(1);
        event.setDateOfCreate(LocalDateTime.now());


        comment.setCommentId(1);
        comment.setDate(new Date(System.currentTimeMillis()));
        comment.setText("nowa wartość");

        when(commentService.update(anyInt(), anyString())).thenReturn(comment);

        when(commentService.update(anyInt(), anyString())).thenReturn(comment);
        mvc.perform(put(apiPath + "/1"+"/"+comment.getText()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@test.com")));

    }


    @Test //ok
    public void deleteComment() throws Exception {
        event.setEventID(1);
        event.setDateOfCreate(LocalDateTime.now());


        comment.setCommentId(1);
        comment.setDate(new Date(System.currentTimeMillis()));

        when(commentService.delete(anyInt(),anyString())).thenReturn(true);

        mvc.perform(delete(apiPath + "/1/"+user.getEmail()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("true")));
    }


}
