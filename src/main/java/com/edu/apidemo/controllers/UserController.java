package com.edu.apidemo.controllers;

import com.edu.apidemo.models.ResponseObject;
import com.edu.apidemo.models.Role;
import com.edu.apidemo.models.User;
import com.edu.apidemo.repositories.RoleRepository;
import com.edu.apidemo.repositories.UserRepository;
import com.edu.apidemo.request.SignInRequest;
import com.edu.apidemo.request.SignUpRequest;
import com.edu.apidemo.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    //new -----
    @Autowired
    private RoleRepository roleRepository;


    //get all users --use
    @GetMapping("/user")
    ResponseEntity<ResponseObject> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
//            List<User> users = userRepository.findUserByStatus(1);
            if (users.size() < 1) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "khong co account/ acount bi khoa cmn het r", "")
                );
            }

            List<UserInfoResponse> userInfoResponseList = new ArrayList<>();
            List<String> abc = new ArrayList<>();
            users.forEach((itemUser) -> {
                UserInfoResponse userInfoResponse = new UserInfoResponse(
                        itemUser.getId(),
                        itemUser.getUsername(),
                        itemUser.getFullname(),
//                        itemUser.getPassword(),
                        itemUser.getYearOfBirth(),
                        itemUser.getRoles().stream().map(itemRole -> itemRole.getRoleName()).collect(Collectors.toList())
                );
                userInfoResponseList.add(userInfoResponse);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "get list all user successfully", userRepository.findAll())
                    new ResponseObject("ok", "get list all user successfully", userInfoResponseList)
            );
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "get list all user failed", "")
            );
        }
    }

    //get user by id --- user
    @GetMapping("/user/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "find user id: " + id + " successfully", userInfoResponse(foundUser.get()))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "not found user id: " + id, "")
        );
    }

//    add new user --- register --- signup --- use
    @PostMapping("/signup")
    ResponseEntity<ResponseObject> addNewUser(@RequestBody SignUpRequest signUpRequest) {
        List<User> listUsers = userRepository.findByUsername(signUpRequest.getUsername().trim());
        if (listUsers.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "username is already exist", "")
            );
        }
        List<Role> roleExist = roleRepository.findAll();
        if (roleExist.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Role is empty...!!!", "")
            );
        }
        User newUser = new User(
                signUpRequest.getUsername().trim(),
                signUpRequest.getFullname().trim(),
                signUpRequest.getYearOfBirth(),
                signUpRequest.getPassword().trim(),
                1
        );

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
/**
        if (strRoles.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "please enter role of account...!!!", "")
            );
        } else  {
            strRoles.forEach(role -> {
                switch (role) {
                    case "manager":
                        Optional<Role> managerRole = roleRepository.findRoleByRolename("manager");
                        if (!managerRole.isPresent()) {
                             ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role manager not exist...!", "")
                            );
                             return;
                        }
                        roles.add(managerRole.get());
                        break;
                    case "admin":
                        Optional<Role> adminRole = roleRepository.findRoleByRolename("admin");
                        if (!adminRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role admin not exist...!", "")
                            );
                            return;
                        }
                        roles.add(adminRole.get());
                        break;
                    case  "mod":
                        Optional<Role> modRole = roleRepository.findRoleByRolename("mod");
                        if (!modRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role mod not exist...!", "")
                            );
                            return;
                        }
                        roles.add(modRole.get());
                        break;
                    case  "staff":
                        Optional<Role> staffRole = roleRepository.findRoleByRolename("staff");
                        if (!staffRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role staffRole not exist...!", "")
                            );
                            return;
                        }
                        roles.add(staffRole.get());
                        break;
                    case  "employee":
                        Optional<Role> employeeRole = roleRepository.findRoleByRolename("employee");
                        if (!employeeRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role employeeRole not exist...!", "")
                            );
                            return;
                        }
                        roles.add(employeeRole.get());
                        break;
                    default:
                        Optional<Role> memberRole = roleRepository.findRoleByRolename("member");
                        if (!memberRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role memberRole not exist...!", "")
                            );
                            return;
                        }
                        roles.add(memberRole.get());
                }
            });
        }
 */

        addRoleUser(strRoles, roles);
        newUser.setRoles(roles);
        userRepository.save(newUser);
/**
        List<User> users = userRepository.findByUsername(signUpRequest.getUsername());
        Set<Role> roleUser = users.get(0).getRoles();
        List<String> rolesUser = new ArrayList<>();
        rolesUser = roleUser.stream().map(item -> item.getRolename()).collect(Collectors.toList());

        UserInfoResponse userInfoResponse = new UserInfoResponse(users.get(0).getId(),
                                                                users.get(0).getUsername(),
                                                                users.get(0).getFullname(),
                                                                users.get(0).getPassword(),
                                                                users.get(0).getYearOfBirth(),
                                                                rolesUser);
*/
        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("ok", "add new user successfully qaz", userRepository.findByUsername(signUpRequest.getUsername()))
//                new ResponseObject("ok", "add new user successfully",userInfoResponse)
                new ResponseObject("ok", "add new user successfully", userInfoResponse(signUpRequest.getUsername()))
        );
    }

    //login --- use
    @PostMapping("/signin")
    ResponseEntity<?> loginServer(@RequestBody SignInRequest signInRequest) {
        try {
            List<User> users = userRepository.findByUsername(signInRequest.getUsername());
            if (signInRequest.getUsername().equals(null) || signInRequest.getUsername().equals("")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "login failed by username is empty", "")
                );
            }
            if (signInRequest.getPassword().equals(null) || signInRequest.getPassword().equals("")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "login failed by password is empty", "")
                );
            }
            if (users.size() > 0) {
                if (users.get(0).getPassword().trim().equals(signInRequest.getPassword().trim())) {
                    UserInfoResponse userInfoResponse = new UserInfoResponse(users.get(0).getId(),
                            users.get(0).getUsername(),
                            users.get(0).getFullname(),
//                            users.get(0).getPassword(),
                            users.get(0).getYearOfBirth(),
                            users.get(0).getRoles().stream().map(item -> item.getRoleName()).collect(Collectors.toList()));
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "login successfully", userInfoResponse)
                    );
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "login failed by password", "")
                );
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "login failed by username", "")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "login failed catch err: " + e, "")
            );
        }
    }

//    insert + update --> upsert --- use
    @PutMapping("/user/{id}")
    ResponseEntity<ResponseObject> upsertUser(@RequestBody SignUpRequest newUser, @PathVariable long id) {
        try {
            Set<String> strRole = newUser.getRoles();
            Set<Role> roles = new HashSet<>();
            addRoleUser(strRole, roles);
            User foundUser = userRepository.findById(id)
                    .map(user -> {
                        user.setUsername(newUser.getUsername().trim());
                        user.setFullname(newUser.getFullname().trim());
                        user.setPassword(user.getPassword().trim());
                        user.setYearOfBirth(newUser.getYearOfBirth());
                        user.setStatus(1);
                        user.setRoles(roles);
                        return userRepository.save(user);
                    }).orElseGet(()->{
//                        newUser.setId(id);
//                        return userRepository.save(newUser);
                        User user = new User();
                        user.setId(id);
                        user.setUsername(newUser.getUsername().trim());
                        user.setFullname(newUser.getFullname().trim());
                        user.setPassword(newUser.getPassword().trim());
                        user.setYearOfBirth(newUser.getYearOfBirth());
                        user.setStatus(1);
                        user.setRoles(roles);
                        return userRepository.save(user);
                    });
//
//            UserInfoResponse userInfoResponse = new UserInfoResponse(
//                    foundUser.getId(),
//                    foundUser.getUsername(),
//                    foundUser.getFullname(),
//                    foundUser.getPassword(),
//                    foundUser.getYearOfBirth(),
//                    foundUser.getStatus(),
//                    roles
//            )

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upsert user successfully", userInfoResponse(foundUser))

            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("faild", "upsert fail err: " + ex.getMessage(), "")
            );
        }
    }

    @DeleteMapping("/user/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) {
        boolean foundUser = userRepository.existsById(id);
        if (foundUser) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete user id: " + id + " successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("faild", "not found user id: " + id, "")
        );
    }
    //delete user
    @DeleteMapping("/user")
    ResponseEntity<ResponseObject> deleteAllUser() {
        userRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "delete all user successfully", "")
        );
    }

    //get user by fullname
    @GetMapping("/user/fullname")
    ResponseEntity<ResponseObject> getUserByFullname(@PathVariable(value = "fullname") String fullname) {
        List<User> users = userRepository.findByFullname(fullname);
        if (users.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "get user by fullname failed", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "get user by fullname successfully", users)
        );
    }

    //new--------------------
    //add role in role
    @PutMapping("/user/newpost/{userId}/role/{roleId}")
    ResponseEntity<ResponseObject> updateRoleRole(@PathVariable(value = "userId") long userId,
                                                  @PathVariable(value = "roleId") long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found user xxxx", "")
            );
        }
        if(!optionalRole.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found role yyy", "")
            );
        }
        User user = userRepository.findById(userId).get();
        Role role = roleRepository.findById(roleId).get();

        user.addRoleIntoUser(role);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "update user role role user successfully", userRepository.save(user))
        );
    }
    //delete role
    @DeleteMapping("/user/newpost/{userId}/role/{roleId}")
    ResponseEntity<ResponseObject> deleteRoleRole(@PathVariable(value = "userId") long userId,
                                                    @PathVariable(value = "roleId") long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found user xxxx", "")
            );
        }
        if(!optionalRole.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found role yyy", "")
            );
        }
        User user = userRepository.findById(userId).get();
        Role role = roleRepository.findById(roleId).get();

        user.deleteRoleIntoUser(role);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "remove role role user user successfully", userRepository.save(user))
        );
    }

    //add
    //delete role
    @PostMapping("/user/newpost/{userId}/role/{roleId}")
    ResponseEntity<ResponseObject> addRoleRole(@PathVariable(value = "userId") long userId,
                                                  @PathVariable(value = "roleId") long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found user xxxx", "")
            );
        }
        if(!optionalRole.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found role yyy", "")
            );
        }
        User user = userRepository.findById(userId).get();
        Role role = roleRepository.findById(roleId).get();

        user.deleteRoleIntoUser(role);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "remove role role user user successfully", userRepository.save(user))
        );
    }

    public Object addRoleUser(Set<String> strRoles,Set<Role> roles) {
        if (strRoles.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "please enter role of account...!!!", "")
            );
        } else  {
            strRoles.forEach(role -> {
                switch (role) {
                    case "manager":
                        Optional<Role> managerRole = roleRepository.findRoleByRoleName("manager");
                        if (!managerRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role manager not exist...!", "")
                            );
                            return;
                        }
                        roles.add(managerRole.get());
                        break;
                    case "admin":
                        Optional<Role> adminRole = roleRepository.findRoleByRoleName("admin");
                        if (!adminRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role admin not exist...!", "")
                            );
                            return;
                        }
                        roles.add(adminRole.get());
                        break;
                    case  "mod":
                        Optional<Role> modRole = roleRepository.findRoleByRoleName("mod");
                        if (!modRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role mod not exist...!", "")
                            );
                            return;
                        }
                        roles.add(modRole.get());
                        break;
                    case  "staff":
                        Optional<Role> staffRole = roleRepository.findRoleByRoleName("staff");
                        if (!staffRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role staffRole not exist...!", "")
                            );
                            return;
                        }
                        roles.add(staffRole.get());
                        break;
                    case  "employee":
                        Optional<Role> employeeRole = roleRepository.findRoleByRoleName("employee");
                        if (!employeeRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role employeeRole not exist...!", "")
                            );
                            return;
                        }
                        roles.add(employeeRole.get());
                        break;
                    default:
                        Optional<Role> memberRole = roleRepository.findRoleByRoleName("member");
                        if (!memberRole.isPresent()) {
                            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                    new ResponseObject("failed", "role memberRole not exist...!", "")
                            );
                            return;
                        }
                        roles.add(memberRole.get());
                }
            });
        }
        return roles;
    }

    public UserInfoResponse userInfoResponse(User user) {
        Set<Role> roleUser = user.getRoles();
        List<String> rolesUser = new ArrayList<>();
        rolesUser = roleUser.stream().map(item -> item.getRoleName()).collect(Collectors.toList());

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
//                user.getPassword(),
                user.getYearOfBirth(),
                rolesUser);
        return userInfoResponse;
    }
    public UserInfoResponse userInfoResponse(List<User> users) {
        Set<Role> roleUser = users.get(0).getRoles();
        List<String> rolesUser = new ArrayList<>();
        rolesUser = roleUser.stream().map(item -> item.getRoleName()).collect(Collectors.toList());

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                users.get(0).getId(),
                users.get(0).getUsername(),
                users.get(0).getFullname(),
//                user.get(0).getPassword(),
                users.get(0).getYearOfBirth(),
                rolesUser);
        return userInfoResponse;
    }

/**
    public UserInfoResponse userInfoResponse(User user) {
        Set<Role> roleUser = user.getRoles();
        List<String> rolesUser = new ArrayList<>();
        rolesUser = roleUser.stream().map(item -> item.getRolename()).collect(Collectors.toList());

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getYearOfBirth(),
                rolesUser);
        return userInfoResponse;
    }
 */
    public UserInfoResponse userInfoResponse(String strRequest) {
        List<User> users = userRepository.findByUsername(strRequest);
        Set<Role> roleUser = users.get(0).getRoles();
        List<String> rolesUser = new ArrayList<>();
        rolesUser = roleUser.stream().map(item -> item.getRoleName()).collect(Collectors.toList());

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                users.get(0).getId(),
                users.get(0).getUsername(),
                users.get(0).getFullname(),
//                user.get(0).getPassword(),
                users.get(0).getYearOfBirth(),
                rolesUser);
        return userInfoResponse;
    }
}
