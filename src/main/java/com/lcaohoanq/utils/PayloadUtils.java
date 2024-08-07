package com.lcaohoanq.utils;

import com.lcaohoanq.views.usersregister.UserRegisterRequest;
import java.util.Map;

public class PayloadUtils {

    public static Map<String, Object> generatePayloadUser(Map<String, Object> payload, UserRegisterRequest user){
        payload.put("id", user.getId());
        payload.put("email", user.getEmail());
        payload.put("phone", user.getPhone());
        payload.put("firstName", user.getFirstName());
        payload.put("lastName", user.getLastName());
        payload.put("password", user.getPassword());
        payload.put("address", user.getAddress());
        payload.put("birthday", user.getBirthday());
        payload.put("gender", user.getGender());
        payload.put("role", user.getRole());
        payload.put("status", user.getStatus());
        payload.put("created_at", user.getCreated_at());
        payload.put("updated_at", user.getUpdated_at());
        payload.put("avatar_url", user.getAvatar_url());
        return payload;
    }

}
