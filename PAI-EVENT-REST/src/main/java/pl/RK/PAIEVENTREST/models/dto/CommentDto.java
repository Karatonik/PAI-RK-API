package pl.RK.PAIEVENTREST.models.dto;

import lombok.Getter;
import lombok.Setter;
import pl.RK.PAIEVENTREST.models.Comment;

import java.util.Date;
import java.util.Set;
@Getter
@Setter
public class CommentDto {

    int commentId;
    String text;
    Date date;
    String userEmailList;

    public CommentDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.text = comment.getText();
        this.date = comment.getDate();
        this.userEmailList=comment.getUserPai().getEmail();
    }

}

