package com.lcaohoanq.schemas;

import com.lcaohoanq.models.Role;
import com.lcaohoanq.models.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    protected Long id;
    protected String email;
    protected String phone;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String address;
    protected String birthday;
    protected String gender;
    protected Role role;
    protected Status status;
    protected String created_at;
    protected String updated_at;
    protected byte[] avatar_url;
    protected int google_account_id;
    protected int facebook_account_id;
}
