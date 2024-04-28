package raf.edu.rs.vebd6.repository.post;

import raf.edu.rs.vebd6.entities.Post;

import java.util.List;

public interface PostRepository {
    public Post addPost(Post post);
    public List<Post> getPosts();
    public Post findPost(Integer id);
    public void deletePost(Integer id);
}
