package com.lcaohoanq.controllers;

import com.lcaohoanq.controllers.PhoneController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${v1API}/otp")
public class OtpController {

    @Autowired
    private MailController mailController;

    @Autowired
    private PhoneController phoneController;

    @GetMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestParam String type, @RequestParam String recipient) {
        switch (type.toLowerCase()) {
            case "mail":
                return mailController.sendOtp(recipient);
            case "phone":
                return phoneController.sendPhoneOtp(recipient);
            default:
                return new ResponseEntity<>("Invalid type specified", HttpStatus.BAD_REQUEST);
        }
    }
}

