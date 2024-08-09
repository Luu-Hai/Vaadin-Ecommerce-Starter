package com.lcaohoanq.schemas;

import com.lcaohoanq.models.Role;
import com.lcaohoanq.models.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequest {

    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    private String birthday;
    private String gender;
    private Role role;
    private Status status;
    private String created_at;
    private String updated_at;
    private byte[] avatar_url;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
