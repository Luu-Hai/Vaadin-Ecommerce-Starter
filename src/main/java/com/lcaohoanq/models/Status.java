package com.lcaohoanq.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Status {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("statusName")
    private UserStatusEnum statusName;

}