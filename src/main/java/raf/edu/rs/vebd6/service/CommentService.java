package raf.edu.rs.vebd6.service;
import raf.edu.rs.vebd6.entities.Comment;
import raf.edu.rs.vebd6.entities.Post;
import raf.edu.rs.vebd6.repository.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    private CommentRepository commentRepository;
    @Inject
    private PostService postService;
    public Comment addComment(Integer id, Comment comment){
        Post p= postService.findPost(id);
        comment.setPostId(id);
        commentRepository.addComment(comment);
        p.getComments().add(comment);
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
