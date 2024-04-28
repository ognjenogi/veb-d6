package raf.edu.rs.vebd6.repository.user;


import raf.edu.rs.vebd6.entities.User;

public interface UserRepository {
    public User findUser(String username);
}
