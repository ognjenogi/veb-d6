package raf.edu.rs.vebd6.service;
import raf.edu.rs.vebd6.entities.Comment;
import raf.edu.rs.vebd6.entities.Post;
import raf.edu.rs.vebd6.repository.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    private CommentRepository commentRepository;
    public Comment addComment(Integer id, Comment comment){
        comment.setPostId(id);
        commentRepository.addComment(comment);
        return comment;
    }

    public List<Comment> getComments() {
        return this.commentRepository.getComments();
    }

    public Comment findComment(Integer id) {
        return this.commentRepository.findComment(id);
    }

    public void deleteComment(Integer id) {
        this.commentRepository.deleteComment(id);
    }
}
