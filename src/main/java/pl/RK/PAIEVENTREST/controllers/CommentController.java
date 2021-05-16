package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.models.dto.CommentDto;
import pl.RK.PAIEVENTREST.services.implementations.CommentServiceImp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("api/comm")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    CommentServiceImp commentService;

    @Autowired
    public CommentController(CommentServiceImp commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{email}/{eventId}/{text}")
    public CommentDto addComment(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                         String email
            , @PathVariable @NotBlank int eventId
            , @PathVariable @Size(min = 1, max = 255) String text) {
        return new CommentDto(commentService.create(email, eventId, text));
    }

    @DeleteMapping("/{commentId}")
    public boolean delete(@PathVariable @NotBlank int commentId) {
        return commentService.delete(commentId);
    }

    @PutMapping("/{commentId}/{text}")
    public CommentDto update(@PathVariable @NotBlank int commentId
            , @PathVariable @Size(min = 1, max = 255) String text) {
        return new CommentDto(commentService.update(commentId, text));
    }
}
