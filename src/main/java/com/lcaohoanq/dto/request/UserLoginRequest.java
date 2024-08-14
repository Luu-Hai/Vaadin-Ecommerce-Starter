package com.lcaohoanq.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String email_phone;
    private String password;
}
