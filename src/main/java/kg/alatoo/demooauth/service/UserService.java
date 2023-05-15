package kg.alatoo.demooauth.service;


import kg.alatoo.demooauth.model.User;

public interface UserService {
    User createUser(User newUser);

    User createAdmin(User newAdmin);

    User findByEmail(String email);
    boolean existsByEmail(String email);
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
