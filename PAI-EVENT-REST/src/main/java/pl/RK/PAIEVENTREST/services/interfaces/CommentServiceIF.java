package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.Comment;

public interface CommentServiceIF {


    Comment set(String userEmail, int eventId, String text);

    boolean delete(int commentId);

    Comment update(int commentId, String text);
}
