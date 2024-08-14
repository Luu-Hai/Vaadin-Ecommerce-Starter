package com.lcaohoanq.controllers;

import com.lcaohoanq.constant.EmailSubject;
import com.lcaohoanq.dto.response.MailResponse;
import com.lcaohoanq.entity.User;
import com.lcaohoanq.enums.EmailBlockReasonEnum;
import com.lcaohoanq.enums.EmailCategoriesEnum;
import com.lcaohoanq.enums.UserStatusEnum;
import com.lcaohoanq.service.MailSenderService;
import com.lcaohoanq.utils.OTPUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RequestMapping(path = "${v1API}/mail")
@RestController
public class MailController {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private HttpServletRequest request;

    //api: /otp/send?type=email&recipient=abc@gmail
    public ResponseEntity<MailResponse> sendOtp(@RequestParam String toEmail) {
        User user = (User) request.getAttribute("validatedEmail");

        if(user.getStatus().getId() == UserStatusEnum.VERIFIED.getStatus()){
            return new ResponseEntity<>(new MailResponse("Email already verified"), HttpStatus.BAD_REQUEST);
        }

        String name = user.getFirstName();
        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        mailSenderService.sendNewMail(toEmail, EmailSubject.subjectGreeting(name),
            EmailCategoriesEnum.OTP.getType(),
            context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/block")
    ResponseEntity<MailResponse> sendBlockAccount(@RequestParam String toEmail) {
        User user = (User) request.getAttribute("validatedEmail");
        Context context = new Context();
        context.setVariable("reason", EmailBlockReasonEnum.ABUSE.getReason());
        mailSenderService.sendNewMail(toEmail, EmailSubject.subjectBlockEmail(user.getFirstName()),
            EmailCategoriesEnum.BLOCK_ACCOUNT.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/forgotPassword")
    ResponseEntity<MailResponse> sendForgotPassword(@RequestParam String toEmail) {
        User user = (User) request.getAttribute("validatedEmail");
        String name = user.getFirstName();
        Context context = new Context();
        String otp = OTPUtils.generateOTP();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        mailSenderService.sendNewMail(toEmail, EmailSubject.subjectGreeting(name), EmailCategoriesEnum.FORGOT_PASSWORD.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}