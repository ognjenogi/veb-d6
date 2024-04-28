package raf.edu.rs.vebd6.repository.post;

import raf.edu.rs.vebd6.entities.Post;
import raf.edu.rs.vebd6.repository.MySqlAbstractRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PostRepMySql  extends MySqlAbstractRepository implements PostRepository {
    @Override
    public Post addPost(Post post) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO posts (author,title, content,date) VALUES(?, ?,?,?)", generatedColumns);
            preparedStatement.setString(1, post.getAuthor());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern("dd:MM:yyyy")));
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                post.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return post;
    }

    @Override
    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from posts");
            while (resultSet.next()) {
                posts.add(new Post(resultSet.getInt("id"),resultSet.getString("author"), resultSet.getString("title"), resultSet.getString("content"),resultSet.getString("date")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return posts;
    }

    @Override
    public Post findPost(Integer id) {
        Post post = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM posts where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                post = new Post(resultSet.getInt("id"),resultSet.getString("author"), resultSet.getString("title"), resultSet.getString("content"),resultSet.getString("date"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return post;
    }

    @Override
    public void deletePost(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM posts where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
