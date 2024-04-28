package raf.edu.rs.vebd6.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Comment {
    private Integer id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private  String text;

    private  String author;
    @NotNull(message = "Title field is required")
    private Integer postId;

    public Comment() {
    }

    public Comment(String text, String author,Integer postId) {
        this();
        this.text = text;
        this.author = author;
        this.postId = postId;
    }

    public Comment(Integer id, String text, String author, Integer postId) {
        this(text,author,postId);
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
