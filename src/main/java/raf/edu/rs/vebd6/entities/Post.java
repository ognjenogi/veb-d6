package raf.edu.rs.vebd6.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private  Integer id;
    private String date;

    private  String author;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private  String title;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private  String content;
    private List<Comment> comments;

    public Post() {
        comments = new ArrayList<>();
    }

    public Post(String author, String title, String content,String date) {
        this();
        this.date = date;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Post(Integer id, String author, String title, String content,String date) {
        this(author, title, content,date);
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
