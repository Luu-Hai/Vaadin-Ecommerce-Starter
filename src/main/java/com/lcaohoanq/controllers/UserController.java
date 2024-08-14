package com.lcaohoanq.controllers;

import com.lcaohoanq.constant.ResourcesPath;
import com.lcaohoanq.dto.base.Response;
import com.lcaohoanq.dto.request.UserRegisterRequest;
import com.lcaohoanq.dto.request.UserUpdatePasswordRequest;
import com.lcaohoanq.dto.response.JwtResponse;
import com.lcaohoanq.dto.request.UserLoginRequest;
import com.lcaohoanq.dto.response.UserResponse;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.exception.MethodArgumentNotValidException;
import com.lcaohoanq.exception.UserNotFoundException;
import com.lcaohoanq.entity.User;
import com.lcaohoanq.repository.UserRepository;
import com.lcaohoanq.utils.ImageCompression;
import com.lcaohoanq.utils.LogUtils;
import com.lcaohoanq.utils.PBKDF2;
import com.lcaohoanq.utils.ValidateUtils;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(path = "${v1API}/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> all() {
        return userRepository.findAll().stream().filter(user -> user.getRole().getRoleName().equals(
            UserRoleEnum.USER)).toList();
    }

    @GetMapping("/{id}")
    User getById(@PathVariable Long id) {

        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/oauth2/callback/google")
    @Async
    public CompletableFuture<ResponseEntity<UserResponse>> createNewAccountGoogle(
        @Valid @RequestBody UserRegisterRequest newUser, BindingResult bindingResult) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (bindingResult.hasErrors()) {
                    log.error("Validation failed for user at: {}", newUser.getCreated_at());
                    throw new MethodArgumentNotValidException(bindingResult);
                }

                if ((newUser.getEmail() == null || newUser.getEmail().isEmpty())) {
                    throw new IllegalArgumentException("Must provide email when login with Google");
                } else {
                    boolean emailExists = userRepository.findAll().stream()
                        .anyMatch(user -> newUser.getEmail().equals(user.getEmail()));
                    if (emailExists) {
                        log.error("Email already Login with Google: {}", newUser.getEmail());
                        return new ResponseEntity<>(new UserResponse("Login Google successfully"),
                            HttpStatus.OK);
                    }
                }

                // Save the user to the repository
                User user = new User();
                user.setId(newUser.getId());
                user.setEmail(newUser.getEmail());
                user.setPhone(newUser.getPhone());
                user.setFirstName(newUser.getFirstName());
                user.setLastName(newUser.getLastName());
                user.setPassword(newUser.getPassword());
                user.setAddress(newUser.getAddress());
                user.setBirthday(newUser.getBirthday());
                user.setGender(newUser.getGender());
                user.setRole(newUser.getRole());
                user.setStatus(newUser.getStatus());
                user.setCreated_at(newUser.getCreated_at());
                user.setUpdated_at(newUser.getUpdated_at());
                user.setAvatar_url(newUser.getAvatar_url());
//                user.setSubscription(newUser.getSubscription());
                user.setGoogle_account_id(newUser.getGoogle_account_id());
                user.setFacebook_account_id(newUser.getFacebook_account_id());

                userRepository.save(user);
                return new ResponseEntity<>(new UserResponse("Login Google successfully"),
                    HttpStatus.OK);
            }  catch(Exception e){
                log.error("An error occurred while creating a new user: {}", e.getMessage());
                return new ResponseEntity<>(new UserResponse(e.getMessage()),
                    HttpStatus.BAD_REQUEST);
            }
        });
    }

    @PostMapping("/register")
    @Async
    public CompletableFuture<ResponseEntity<UserResponse>> createNew(
        @Valid @RequestBody UserRegisterRequest newUser, BindingResult bindingResult) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Request body: " + newUser.toString());

            try {
                if (bindingResult.hasErrors()) {
                    LogUtils.showLogValidationFailed(newUser.getCreated_at());
                    throw new MethodArgumentNotValidException(bindingResult);
                }

                if (StringUtils.isBlank(newUser.getEmail()) && StringUtils.isBlank(newUser.getPhone())) {
                    throw new IllegalArgumentException("Either email or phone must be provided.");
                } else {
                    boolean emailExists = false;
                    boolean phoneExists = false;

                    // Assuming userRepository has methods to find by email and phone
                    if (StringUtils.isNotBlank(newUser.getEmail())) {
                        emailExists = userRepository.findByEmail(newUser.getEmail()) != null;
                    }
                    if (StringUtils.isNotBlank(newUser.getPhone())) {
                        phoneExists = userRepository.findByPhone(newUser.getPhone()) != null;
                    }

                    if (emailExists) {
                        LogUtils.showLogExistedUser("Email", newUser.getEmail());
                        return new ResponseEntity<>(new UserResponse("Email already exists"), HttpStatus.BAD_REQUEST);
                    }
                    if (phoneExists) {
                        LogUtils.showLogExistedUser("Phone number", newUser.getPhone());
                        return new ResponseEntity<>(new UserResponse("Phone number already exists"), HttpStatus.BAD_REQUEST);
                    }
                }


                // Hash the password before saving the user
                newUser.setPassword(new PBKDF2().hash(newUser.getPassword().toCharArray()));
                newUser.setAvatar_url(ImageCompression.compressImage(ResourcesPath.AVATAR_ANONYMOUS));

                // Save the user to the repository
                User user = new User();
                user.setId(newUser.getId());
                user.setEmail(newUser.getEmail());
                user.setPhone(newUser.getPhone());
                user.setFirstName(newUser.getFirstName());
                user.setLastName(newUser.getLastName());
                user.setPassword(newUser.getPassword());
                user.setAddress(newUser.getAddress());
                user.setBirthday(newUser.getBirthday());
                user.setGender(newUser.getGender());
                user.setRole(newUser.getRole());
                user.setStatus(newUser.getStatus());
                user.setCreated_at(newUser.getCreated_at());
                user.setUpdated_at(newUser.getUpdated_at());
                user.setAvatar_url(newUser.getAvatar_url());
//                user.setSubscription(newUser.getSubscription());
                user.setGoogle_account_id(newUser.getGoogle_account_id());
                user.setFacebook_account_id(newUser.getFacebook_account_id());

                System.out.println("Request body after processing: " + newUser);

                userRepository.save(user);

                LogUtils.showLogNewUserRegistered(newUser.getCreated_at());

                return new ResponseEntity<>(new UserResponse("Register successfully"),
                    HttpStatus.OK);
            }  catch(Exception e){
                LogUtils.showLogErrorWhenRegisterNewUser(newUser.getCreated_at(), e.getMessage());
                return new ResponseEntity<>(new UserResponse(e.getMessage()),
                    HttpStatus.BAD_REQUEST);
            }
        });
    }

    @PostMapping("/login")
    @Async
    public CompletableFuture<ResponseEntity<? extends Response>> login(@Valid @RequestBody UserLoginRequest user,
        BindingResult bindingResult) {
        return CompletableFuture.supplyAsync(() -> {
            User userFound;

            if (bindingResult.hasErrors()) {
                LogUtils.showLogValidationFailed(LocalDateTime.now().toString());
                throw new MethodArgumentNotValidException(bindingResult);
            }

            // Check email or phone
            String emailOrPhone = user.getEmail_phone();
            boolean isEmail = ValidateUtils.checkTypeAccount(emailOrPhone);

            userFound = isEmail ? userRepository.findByEmail(emailOrPhone) : userRepository.findByPhone(emailOrPhone);

            if (userFound == null) {
                String notFoundMessage = isEmail ? "Email not found: " : "Phone number not found: ";
                log.error("{}{}", notFoundMessage, emailOrPhone);
                String responseMessage = isEmail ? "Email not found" : "Phone number not found";
                return new ResponseEntity<>(new UserResponse(responseMessage), HttpStatus.BAD_REQUEST);
            }

            if (!ValidateUtils.authenticate(user.getPassword(), userFound.getPassword())) {
                log.error("Password wrong: {}", user.getEmail_phone());
                return new ResponseEntity<>(new UserResponse("Password wrong"),
                    HttpStatus.BAD_REQUEST);
            }

            LogUtils.showLogLoginSuccess(LocalDateTime.now().toString());

            // Generate tokens
            JwtResponse jwtResponse = new JwtResponse("accessToken", "refreshToken");

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }).orTimeout(10, TimeUnit.SECONDS).exceptionally(ex -> {
            if(ex instanceof TimeoutException){
                LogUtils.showLogTimeOutException(LocalDateTime.now().toString());
                return new ResponseEntity<>(new UserResponse("TimeoutException"),
                    HttpStatus.REQUEST_TIMEOUT);
            } else {
                LogUtils.showLogInternalServerException(LocalDateTime.now().toString());
                return new ResponseEntity<>(new UserResponse(ex.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    User updateOrCreate(@RequestBody User newUser, @PathVariable Long id) {

        return userRepository.findById(id)
            .map(item -> {
                item.setFirstName(newUser.getFirstName());
                return userRepository.save(item);
            })
            .orElseGet(() -> {
                newUser.setId(id);
                return userRepository.save(newUser);
            });
    }

    //update user password
    @PostMapping("/updatePassword")
    public ResponseEntity<UserResponse> updatePassword(@RequestBody UserUpdatePasswordRequest user) {
        User data;

        if(ValidateUtils.checkTypeAccount(user.getIdentifier())){
            data = userRepository.findByEmail(user.getIdentifier());
        }else{
            data = userRepository.findByPhone(user.getIdentifier());
        }

        if(data == null){
            return new ResponseEntity<>(new UserResponse("User not found"),
                HttpStatus.BAD_REQUEST);
        }

        data.setPassword(user.getNewPassword());
        userRepository.save(data);

        return new ResponseEntity<>(new UserResponse("Password updated successfully"),
            HttpStatus.OK);
    }

}
