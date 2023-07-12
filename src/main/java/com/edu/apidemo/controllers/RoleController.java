package com.edu.apidemo.controllers;


import com.edu.apidemo.models.ResponseObject;
import com.edu.apidemo.models.Role;
import com.edu.apidemo.models.User;
import com.edu.apidemo.repositories.RoleRepository;
import com.edu.apidemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class RoleController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")//get all roles
    ResponseEntity<ResponseObject> getAllRoles() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "get all roles successfullly", roleRepository.findAll())
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "get list role failed", "")
            );
        }
    }

//    get all user by role id
    @GetMapping("/role/{roleId}/user")
    public ResponseEntity<ResponseObject> getAllUserByRoleId(@PathVariable(value = "roleId") Long roleId) {
        if(!roleRepository.existsById(roleId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found user by role id: " + roleId, "")
            );
        }
        List<User> users = userRepository.findUserByRolesId(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "find users by role id: " + roleId, users)
        );
    }
    //get user by role id --. user controller
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<ResponseObject> getUserByRoleId(@PathVariable(value = "userId") long userId) {
//        Optional<User> foundUser = userRepository.findById(userId);
//        if (foundUser.isPresent()) {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "found user by id successfully", foundUser)
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ResponseObject("failed", "not found user", "")
//            );
//        }
//    }
    @GetMapping("/user/{userId}/role")
    public ResponseEntity<ResponseObject> getAllRoleByUserId(@PathVariable(value = "userId") long userId) {
        if(!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found user by id: " + userId, "")
            );
        }
        List<Role> roles = roleRepository.findRoleByUsersId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "get all role by user id successfully", roles)
        );
    }

    @PostMapping("/user/{userId}/role")
    public ResponseEntity<ResponseObject> addRole(@PathVariable(value = "userId") long userId, @RequestBody User roleRequest) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (!foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found user by id abc", "")
            );
        }

//        Role role = userRepository.findById(userId)
        return null;
    }
}
