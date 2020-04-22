package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.config.Constants;
import com.tranquocdai.freshmarket.dto.UserDTO;
import com.tranquocdai.freshmarket.dto.UserFirstInfo;
import com.tranquocdai.freshmarket.dto.UserInfoDTO;
import com.tranquocdai.freshmarket.model.Avatar;
import com.tranquocdai.freshmarket.model.RoleUser;
import com.tranquocdai.freshmarket.model.User;
import com.tranquocdai.freshmarket.repository.AvatarRepository;
import com.tranquocdai.freshmarket.repository.RoleResponsitory;
import com.tranquocdai.freshmarket.repository.UserRepository;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import com.tranquocdai.freshmarket.service.BaseService;
import com.tranquocdai.freshmarket.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

    @Autowired
    AvatarRepository avatarRepository;

    @Autowired
    StorageService storageService;

    @GetMapping("/users")
    public ResponseEntity getAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            List<User> userListPipe = new ArrayList<>();
            userList.forEach((element) -> {
                if (element.getRoleUser().getRoleID() != Constants.ID_ROLE_ADMIN)
                    userListPipe.add(element);
            });
            return new ResponseEntity(new SuccessfulResponse(userListPipe), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/search")
    public ResponseEntity getAllUserBySearch(@RequestParam(value = "keySearch", defaultValue = "") String keyword) {
        try {
            List<User> userList = userRepository.findByFullNameContains(keyword);
            List<User> userListPipe = new ArrayList<>();
            userList.forEach((element) -> {
                if (element.getRoleUser().getRoleID() != Constants.ID_ROLE_ADMIN)
                    userListPipe.add(element);
            });
            return new ResponseEntity(new SuccessfulResponse(userListPipe), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/fist")
    public ResponseEntity createUserFistInfo(@Valid @RequestBody UserFirstInfo userAddDTO) {
        try {
            if (userRepository.findByUserName(userAddDTO.getUserName()).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            RoleUser roleUser = roleResponsitory.findByRoleID(Constants.ID_ROLE_DEFAULT).get();
            User user = new User();
            user.setUserName(userAddDTO.getUserName());
            user.setPassword(passwordEncoder.encode(userAddDTO.getPassword()));
            user.setRoleUser(roleUser);
            Avatar avatar = new Avatar();
            avatar = avatarRepository.findById(Constants.ID_IMAGE_DEFAULT).get();
            user.setAvatar(avatar);
            userRepository.save(user);
            User result = userRepository.findByUserName(userAddDTO.getUserName()).get();
            UserInfoDTO userDTO = UserInfoDTO.converUser(result);
            return new ResponseEntity(new SuccessfulResponse(userDTO), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "create user not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userAddDTO) {
        try {
            if (userRepository.findByUserName(userAddDTO.getUserName()).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            RoleUser roleUser = roleResponsitory.findByRoleID(userAddDTO.getRoleID()).get();
            User user = new User();
            user.setUserName(userAddDTO.getUserName());
            user.setFullName(userAddDTO.getFullName());
            user.setPassword(passwordEncoder.encode(userAddDTO.getPassword()));
            user.setEmail(userAddDTO.getEmail());
            user.setPhoneNumber(userAddDTO.getPhoneNumber());
            user.setRoleUser(roleUser);
            Avatar avatar = new Avatar();
            if (userAddDTO.getImageBase64() != null || "".equals(userAddDTO.getImageBase64())) {
                String newImageUrl = storageService.store(userAddDTO.getImageBase64());
                avatar.setUrl(newImageUrl);
                avatarRepository.save(avatar);
            } else {
                avatar = avatarRepository.findById(Constants.ID_IMAGE_DEFAULT).get();
            }
            user.setAvatar(avatar);
            userRepository.save(user);
            User result = userRepository.findByUserName(userAddDTO.getUserName()).get();
            UserInfoDTO userDTO = UserInfoDTO.converUser(result);
            return new ResponseEntity(new SuccessfulResponse(userDTO), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "create user not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/{usersID}")
    public ResponseEntity updateUserByID(@Valid @RequestBody UserDTO updateUserDTO, @PathVariable("usersID") Long usersID) {
        try {
            if (!userRepository.findById(usersID).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            User user = userRepository.findById(usersID).get();
            user.setFullName(updateUserDTO.getFullName());
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
            user.setEmail(updateUserDTO.getEmail());
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
            RoleUser roleUser = roleResponsitory.findById(updateUserDTO.getRoleID()).get();
            user.setRoleUser(roleUser);
            if (updateUserDTO.getImageBase64() != null && !"".equals(updateUserDTO.getImageBase64())) {
                Avatar avatar = user.getAvatar();
                if (Constants.URL_POST_DEFAULT.equals(avatar.getUrl())) {
                    Avatar avatarNew = new Avatar();
                    String newImageUrl = storageService.store(updateUserDTO.getImageBase64());
                    avatarNew.setUrl(newImageUrl);
                    avatarRepository.save(avatarNew);
                    user.setAvatar(avatarNew);
                } else {
                    storageService.delete(avatar.getUrl());
                    String newImageUrl = storageService.store(updateUserDTO.getImageBase64());
                    avatar.setUrl(newImageUrl);
                    avatarRepository.save(avatar);
                    user.setAvatar(avatar);
                }
            }
            userRepository.save(user);
            User result = userRepository.findById(usersID).get();
            UserInfoDTO userDTO = UserInfoDTO.converUser(result);
            return new ResponseEntity(new SuccessfulResponse(userDTO), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "create user not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users")
    public ResponseEntity updateUser(Authentication authentication, @Valid @RequestBody UserDTO updateUserDTO) {
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
            if (updateUserDTO.getImageBase64() != null && "".equals(updateUserDTO.getImageBase64())) {
                Avatar avatar = user.getAvatar();
                if (Constants.URL_POST_DEFAULT.equals(avatar.getUrl())) {
                    Avatar avatarNew = new Avatar();
                    String newImageUrl = storageService.store(updateUserDTO.getImageBase64());
                    avatarNew.setUrl(newImageUrl);
                    avatarRepository.save(avatarNew);
                    user.setAvatar(avatarNew);
                } else {
                    storageService.delete(avatar.getUrl());
                    String newImageUrl = storageService.store(updateUserDTO.getImageBase64());
                    avatar.setUrl(newImageUrl);
                    avatarRepository.save(avatar);
                    user.setAvatar(avatar);
                }
            }
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