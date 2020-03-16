package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.dto.UserDTO;
import com.tranquocdai.freshmarket.dto.UserInfoDTO;
import com.tranquocdai.freshmarket.model.RoleUser;
import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.RoleResponsitory;
import com.tranquocdai.freshmarket.repository.UserRepository;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import com.tranquocdai.freshmarket.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleResponsitory roleResponsitory;

    @Autowired
    BaseService baseService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity getAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            return new ResponseEntity(new SuccessfulResponse(userList), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody AddUserDTO addUserDTO) {
        try {
            if (userRepository.findByUserName(addUserDTO.getUserName()).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            RoleUser roleUser = roleResponsitory.findByRoleID(addUserDTO.getRoleID()).get();
            User user = new User();
            user.setUserName(addUserDTO.getUserName());
            user.setFullName(addUserDTO.getFullName());
            user.setPassword(passwordEncoder.encode(addUserDTO.getPassword()));
            user.setEmail(addUserDTO.getEmail());
            user.setPhoneNumber(addUserDTO.getPhoneNumber());
            user.setRoleUser(roleUser);
            userRepository.save(user);
            User result = userRepository.findByUserName(addUserDTO.getUserName()).get();
            UserInfoDTO userDTO = UserInfoDTO.converUser(result);
            return new ResponseEntity(new SuccessfulResponse(userDTO), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "create user not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users")
    public ResponseEntity updateUser(Authentication authentication, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        try {
            if (authentication == null || "".equals(authentication)) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "authorization has not been registered");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            RoleUser roleUser = roleResponsitory.findByRoleID(updateUserDTO.getRoleID()).get();
            User user = baseService.getUser(authentication).get();
            user.setFullName(updateUserDTO.getFullName());
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
            user.setEmail(updateUserDTO.getEmail());
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
            user.setRoleUser(roleUser);
            userRepository.save(user);
            User result = baseService.getUser(authentication).get();
            return new ResponseEntity(new SuccessfulResponse(UserInfoDTO.converUser(result)), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "update user not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/information")
    public ResponseEntity readUser(Authentication authentication) {
        try {
            Optional<User> user = baseService.getUser(authentication);
            return new ResponseEntity(new SuccessfulResponse(user), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{userID}")
    public ResponseEntity deleteUser(@PathVariable("userID") Long userID) {
        try {
            if (!userRepository.findById(userID).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            userRepository.deleteById(userID);
            return new ResponseEntity(new SuccessfulResponse("delete successfully"), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}

class AddUserDTO {
    @NotEmpty
    @Size(min = 2)
    private String userName;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotEmpty
    private String fullName;

    @Size(min = 9)
    private String phoneNumber;

    @Size(min = 2)
    private String email;

    private Long roleID;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }
}

class UpdateUserDTO {
    @NotEmpty
    @Size(min = 8)
    private String password;

    private String fullName;

    @Size(min = 9)
    private String phoneNumber;

    @Size(min = 2)
    private String email;

    private Long roleID;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }
}