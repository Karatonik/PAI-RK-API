package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.RK.PAIEVENTREST.models.dto.CommentDto;
import pl.RK.PAIEVENTREST.services.implementations.CommentServiceImp;

@RestController("/comm")
public class CommentController {

    CommentServiceImp commentService;
    @Autowired
    public CommentController(CommentServiceImp commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDto addComment(String email, int eventId ,@RequestBody String text){
        return new CommentDto( commentService.set(email,eventId,text));
    }
}
