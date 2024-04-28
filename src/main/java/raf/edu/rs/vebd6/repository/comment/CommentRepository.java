package raf.edu.rs.vebd6.repository.comment;

import raf.edu.rs.vebd6.entities.Comment;

import java.util.List;

public interface CommentRepository {
    public Comment addComment(Comment comment);
    public List<Comment> getComments();
    public Comment findComment(Integer id);
    public void deleteComment(Integer id);
}
