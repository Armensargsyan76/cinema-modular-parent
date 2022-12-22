package am.itspace.cinemamodularcommon.repository;


import am.itspace.cinemamodularcommon.entity.filmdetail.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByUserId(int userId);

    List<Comment> findAllByFilmId(int filmId);

}
