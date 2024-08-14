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

@RequestMapping(path = "${v1API}/employee")
@RestController
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> getEmployee() {
        return userRepository.findAll().stream().filter(user -> user.getRole().getRoleName().equals(
            UserRoleEnum.EMPLOYEE)).toList();
    }

    @PostMapping("/register")
    public void registerEmployee() {
        // Register employee
    }

}
