package com.lcaohoanq.dto.request;


import com.lcaohoanq.entity.Role;
import com.lcaohoanq.entity.Status;
import com.lcaohoanq.enums.UserGenderEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class UserRegisterRequest {
    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    private String birthday;
    private UserGenderEnum gender;
    private Role role;
    private Status status;
    private String created_at;
    private String updated_at;
    private byte[] avatar_url;
    private int google_account_id;
    private int facebook_account_id;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
