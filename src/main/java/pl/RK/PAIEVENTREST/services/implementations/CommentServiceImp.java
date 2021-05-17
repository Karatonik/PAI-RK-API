package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.Comment;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.repositorys.CommentRepository;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.CommentServiceIF;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentServiceIF {

    CommentRepository commentRepository;
    UserPaiRepository userPaiRepository;
    EventPaiRepository eventPaiRepository;
    @Autowired
    public CommentServiceImp(CommentRepository commentRepository,UserPaiRepository userPaiRepository,
                             EventPaiRepository eventPaiRepository) {
        this.commentRepository = commentRepository;
        this.userPaiRepository=userPaiRepository;
        this.eventPaiRepository=eventPaiRepository;
    }


    @Override
    public Comment create(String userEmail, int eventId, String text) {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(userEmail);
        Optional<EventPAI> optionalEventPAI = eventPaiRepository.findById(eventId);
        if(optionalUserPAI.isPresent()&&optionalEventPAI.isPresent()) {
            return commentRepository.save(new Comment(text, optionalEventPAI.get(), optionalUserPAI.get()));
        }
        return new Comment();
    }

    @Override
    public boolean delete(int commentId ,String email) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Optional<UserPAI> optionalUserPAI =userPaiRepository.findById(email);
        if(optionalComment.isPresent()&& optionalUserPAI.isPresent()){
            Comment comment = optionalComment.get();
            UserPAI user = optionalUserPAI.get();
            if(comment.getUserPai().equals(user)){
                commentRepository.delete(optionalComment.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public Comment update(int commentId, String text) {
        Optional<Comment>optionalComment=commentRepository.findById(commentId);
        if(optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            comment.setText(text);
            comment.setDate(new Date(System.currentTimeMillis()));
         return  commentRepository.save(comment);
        }
        return null;
    }
}
