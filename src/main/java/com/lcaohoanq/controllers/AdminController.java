package com.lcaohoanq.controllers;

import com.lcaohoanq.entity.User;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "${v1API}/admin")
@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> getAdmin() {
        return userRepository.findAll().stream().filter(user -> user.getRole().getRoleName().equals(
            UserRoleEnum.ADMIN)).toList();
    }

    @PostMapping("/register")
    public void registerAdmin() {
        // Register admin
    }

}
