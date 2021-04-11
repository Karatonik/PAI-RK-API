package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.dto.CommentDto;
import pl.RK.PAIEVENTREST.services.implementations.CommentServiceImp;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("api/comm")
@EnableSwagger2
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

    @DeleteMapping
    public boolean delete(int commentId){
        return  commentService.delete(commentId);    }

    @PutMapping
    public CommentDto update(int commentId ,String text){
        return new CommentDto(commentService.update(commentId , text));
    }
}
