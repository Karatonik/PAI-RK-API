package pl.RK.PAIEVENTREST.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.RK.PAIEVENTREST.models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
