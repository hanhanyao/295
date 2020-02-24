package com.stockApp.demo.service;
import com.stockApp.demo.repository.*;
import com.stockApp.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> convertuserToMap(User user) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(user == null){
            return map;
        }

        map.put("id", user.getId());
        map.put("Screename", user.getUsername());
        map.put("email", user.getEmail());
        map.put("name", user.getName());
        map.put("BusinessTitle", user.getBusinessTitle());
        map.put("Description", user.getAboutMe());
        map.put("portrait", user.getPortrait());

        return map;
    }

    public Map<String, Object> convertRoleToMap(long uid, String sessionId, String username) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("uid", uid);
        map.put("sessionId", sessionId);
        map.put("username", username);
        return map;
    }


    @Transactional
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User getUser(long id) {
        return userRepository.getById(id);
        // return userRepository.getOne(id);
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Transactional
    public boolean existUser(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Transactional
    public boolean existId(long id) {
        return userRepository.existsById(id);
    }

    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public void verifyUser(User user) {

    }


    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void invite(long uid, String email) {

    }


}
