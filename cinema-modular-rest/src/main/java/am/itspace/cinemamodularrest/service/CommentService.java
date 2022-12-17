package am.itspace.cinemamodularrest.service;


import am.itspace.cinemamodularcommon.entity.filmdetail.Comment;
import am.itspace.cinemamodularcommon.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentByUserId(int id) {
        return commentRepository.findAllByUserId(id);
    }
}
