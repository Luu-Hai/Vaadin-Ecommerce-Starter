package com.lcaohoanq.controllers;

import com.lcaohoanq.constant.EmailSubject;
import com.lcaohoanq.dto.response.ForgotPasswordResponse;
import com.lcaohoanq.entity.User;
import com.lcaohoanq.enums.EmailCategoriesEnum;
import com.lcaohoanq.repository.UserRepository;
import com.lcaohoanq.service.MailSenderService;
import com.lcaohoanq.utils.OTPUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RequestMapping(path = "${v1API}/forgotPassword")
@RestController
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MailSenderService mailSenderService;

    @GetMapping("")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestParam @Validated String email_phone) {
        User user = (User) request.getAttribute("validatedAccount");

        String name = user.getFirstName();
        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", name);
        context.setVariable("otp", otp);

        if(user.getEmail() != null){
            mailSenderService.sendNewMail(user.getEmail(), EmailSubject.subjectGreeting(name),
                EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                context);
        } else {
            mailSenderService.sendNewMail(user.getPhone(), EmailSubject.subjectGreeting(name),
                EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                context);
        }

        ForgotPasswordResponse response = new ForgotPasswordResponse("Forgot password sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
