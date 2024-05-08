package raf.edu.rs.vebd6.service;
import raf.edu.rs.vebd6.entities.Comment;
import raf.edu.rs.vebd6.entities.Post;
import raf.edu.rs.vebd6.repository.comment.CommentRepository;
import raf.edu.rs.vebd6.repository.post.PostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostService {
    @Inject
    private PostRepository postRepository;
    @Inject
    private CommentRepository commentRepository;
    public Post addPost(Post post) {
        return this.postRepository.addPost(post);
    }

    public List<Post> getPosts() {
        return this.postRepository.getPosts();
    }

    public Post findPost(Integer id) {
        Post p =this.postRepository.findPost(id);
        for (Comment c: commentRepository.getComments()) {
            if(c.getPostId().equals(id)) p.getComments().add(c);
        }
        return p;
    }

    public void deletePost(Integer id) {
        this.postRepository.deletePost(id);
    }
}
