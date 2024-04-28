package raf.edu.rs.vebd6.repository.comment;
import raf.edu.rs.vebd6.entities.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommentRepImpl implements CommentRepository{
    private static List<Comment> comments = new CopyOnWriteArrayList<>();

    @Override
    public Comment addComment(Comment comment) {
        Integer id = comments.size();
        comment.setId(id);
        comments.add(Math.toIntExact(id), comment);

        return comment;
    }

    @Override
    public List<Comment> getComments() {
        return  new ArrayList<>(comments);
    }

    @Override
    public Comment findComment(Integer id) {
        return comments.get(id);
    }

    @Override
    public void deleteComment(Integer id) {
        comments.remove(id.intValue());
    }
}
