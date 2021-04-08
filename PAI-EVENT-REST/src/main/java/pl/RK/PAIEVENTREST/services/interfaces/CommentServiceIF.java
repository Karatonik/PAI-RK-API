package pl.RK.PAIEVENTREST.services.interfaces;

import pl.RK.PAIEVENTREST.models.Comment;

public interface CommentServiceIF {



    public Comment set(String userEmail , int eventId , String text );

    public boolean delete(int commentId);

    public Comment update(int commentId , String text);
}
