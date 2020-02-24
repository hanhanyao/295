package com.stockApp.demo.controller;
import com.stockApp.demo.model.*;
import com.stockApp.demo.service.*;
import com.stockApp.demo.aspect.*;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/__health", method = RequestMethod.GET)
    public String checkHealth() {
        return "all is well.";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, Object> login, HttpSession session) {
        if (login.containsKey("email") && login.containsKey("password")) {
            String email = (String) login.get("email");
            String password = (String) login.get("password");
            if (userService.existUser(email)) {
                User user = userService.getUserByEmail(email);
                String hashcode = user.getHashcode();

                if (BCrypt.checkpw(password, hashcode)) {
                    String sessionId = UUID.randomUUID().toString();

                    long uid = user.getId();
                    session.setAttribute("uid", uid);
                    System.out.println(session.getId());
                   /* String regex = "^(.+)@sjsu.edu$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email);
                    if (matcher.matches()) {
                        role = "AdminUser";
                    } else {
                        role = "User";
                    }*/


                    Map<String, Object> res = userService.convertRoleToMap(uid,  sessionId, user.getUsername());

                    System.out.println("login sessionId:" + sessionId);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("password is not correct", HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>("email is not correct", HttpStatus.NO_CONTENT);
            }

        } else {
            return new ResponseEntity<>("emaill or password can not be null", HttpStatus.NO_CONTENT);
        }


    }

    /**
     * Sample test
     * POST: http://localhost:8080/registration
     * payload: {
     *      "username": "ABC",
     *      "email": "xxx",
     *      "password": "yyy",
     *      "name": "Alice",
     *      "businessTitle" : "Software Manager",
     *      "aboutMe": "love coding"
     * }
     * Description: register a user
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> registeration(@RequestBody Map<String, Object> registration) {
        System.out.println("enter registration.");
        if (!registration.containsKey("email") || !registration.containsKey("username") || !registration.containsKey("password")) {
            System.out.println("null");
            return new ResponseEntity<>("email or password or username cannot be null", HttpStatus.BAD_REQUEST);
        } else {
            String email = (String) registration.get("email");
            String password = (String) registration.get("password");
            String username = (String) registration.get("username");
            if (userService.existUser(email)) {
                return new ResponseEntity<>("email is already exists", HttpStatus.BAD_REQUEST);
            } else {

                String hashcode = BCrypt.hashpw(password, BCrypt.gensalt(11));
                // check email

                User user = new User();
                user.setEmail(email);
                user.setHashcode(hashcode);
                user.setUsername(username);

                if (registration.containsKey("name")) {
                    String name = (String) registration.get("name");
                    user.setName(name);
                }
                if (registration.containsKey("portrait")) {
                    String portrait = (String) registration.get("portrait");
                    user.setPortrait(portrait);
                }
                if (registration.containsKey("businessTitle")) {
                    String businessTitle = (String) registration.get("businessTitle");
                    user.setBusinessTitle(businessTitle);
                }
                if (registration.containsKey("aboutMe")) {
                    String aboutMe = (String) registration.get("aboutMe");
                    user.setAboutMe(aboutMe);
                }

                userService.createUser(user);
                userService.verifyUser(user);
                return new ResponseEntity<>("successful egistration", HttpStatus.OK);
            }
        }
    }

    /**
     * Sample test
     * GET: http://localhost:8080/verifyUser?uid=7
     * Description: get user info
     */


    /**
     * Sample test
     * GET: http://localhost:8080/userProfile?uid=7
     * Description: get user info
     */
    @GetLoggedInRequired
    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(HttpSession session, @RequestParam long uid) {
        //aop uid
        if(userService.existId(uid)) {
            User user= userService.getUser(uid);
            System.out.println(user.toString());
            return new ResponseEntity<>(userService.convertuserToMap(user) , HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("id does not exist", HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Sample test
     * POST: http://localhost:8080/logout
     * payload: {
     *     uid: 9
     * }
     * Description: logout
     */
    @PostLoggedInRequired
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(@RequestBody Map<String, Object> payload, HttpSession session){
        //aop uid
        long uid = Long.valueOf(String.valueOf(payload.get("uid")));
        session.removeAttribute("uid");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Sample test
     * POST: http://localhost:8080/userProfile
     * payload: {
     *      "uid": 10,
     *      "name": "Alice",
     *      "businessTitle" : "Software Manager",
     *      "aboutMe": "love coding"
     * }
     * Description: update a user profile
     */
    @PostLoggedInRequired
    @RequestMapping(value = "/userProfile", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> payload, HttpSession session) {
        //aop uid
        if(!payload.containsKey("uid")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        long uid = Long.valueOf(String.valueOf(payload.get("uid")));
        if(!userService.existId(uid)) {
            return new ResponseEntity<>("id does not exist", HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUser(uid);
        if(payload.containsKey("aboutMe")){
            user.setAboutMe((String) payload.get("aboutMe"));
        }
        if(payload.containsKey("name")){
            user.setName((String) payload.get("name"));
        }
        if(payload.containsKey("portrait")){
            user.setPortrait((String) payload.get("portrait"));
        }
        if(payload.containsKey("businessTitle")){
            user.setBusinessTitle((String) payload.get("businessTitle"));
        }

        userService.updateUser(user);
        return new ResponseEntity<>(userService.convertuserToMap(user), HttpStatus.OK);
    }


    /**
     * Sample test
     * GET: http://localhost:8080/get_all_users
     * Description: return all users
     */
    @RequestMapping(value="/get_all_users", method=RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAll();
        List<Map<String, Object>> list = new ArrayList<>();
        for (User user : users) {
            list.add(userService.convertuserToMap(user));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }





}

