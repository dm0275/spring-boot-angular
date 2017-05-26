package com.gmail.dm0275.controller;

import com.gmail.dm0275.model.User;
import com.gmail.dm0275.service.UserService;
import com.gmail.dm0275.util.CustomErrorType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder componentsBuilder) {
        logger.info("Creating User: {}", user);

        if (userService.isUserExist(user)) {
            logger.error("Unable to create user. A User with name {} already exists.", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create user. A User with name " +
                    user.getName() + " already exists."), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);

        User currentUser = userService.findById(id);
        if (currentUser == null) {
            logger.error("Unable to update user with id {}. User was not found", id);
            return new ResponseEntity(new CustomErrorType("Unable to update user with id " + id + ". User was not found"), HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & deleting User with id {}", id);

        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete user with id {}. User was not found", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete user with id " + id + ". User was not found"), HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
