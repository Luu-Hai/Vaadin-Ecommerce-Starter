package com.lcaohoanq.dto.base;

import com.lcaohoanq.entity.User;

public class Request extends User {
    public Request(String email_phone, String password) {
        super(email_phone, password);
    }
}
