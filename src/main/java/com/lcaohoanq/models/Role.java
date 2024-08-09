package com.lcaohoanq.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Role {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("roleName")
    private UserRoleEnum roleName;

}
