package com.stockApp.demo.service;
import com.stockApp.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User User);

    User getUser(long id);

    User getUserByEmail(String email);
    boolean existUser(String email);
    boolean existId(long id);
    List<User> getAll();
    Map<String, Object> convertuserToMap(User user);
    Map<String, Object> convertRoleToMap(long uid, String sessionId, String username);
    User getUserByUsername(String username);
    void verifyUser(User user);

    void updateUser(User user);
    void invite(long uid, String email);

}
