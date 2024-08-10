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
public class UserRegisterRequest extends RegisterRequest {

    public UserRegisterRequest(){
        super();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
