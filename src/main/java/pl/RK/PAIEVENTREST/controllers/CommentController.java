package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.dto.CommentDto;
import pl.RK.PAIEVENTREST.services.implementations.CommentServiceImp;

@RestController
@RequestMapping("api/comm")
@CrossOrigin(origins = "http://eventporoj.tk")
public class CommentController {

    CommentServiceImp commentService;

    @Autowired
    public CommentController(CommentServiceImp commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{email}/{eventId}/{text}")
    public CommentDto addComment(@PathVariable String email, @PathVariable int eventId, @PathVariable String text) {
        return new CommentDto(commentService.create(email, eventId, text));
    }

    @DeleteMapping("/{commentId}")
    public boolean delete(@PathVariable int commentId) {
        return commentService.delete(commentId);
    }

    @PutMapping("/{commentId}/{text}")
    public CommentDto update(@PathVariable int commentId, @PathVariable String text) {
        return new CommentDto(commentService.update(commentId, text));
    }
}
