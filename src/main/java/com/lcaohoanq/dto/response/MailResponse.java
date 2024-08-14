package com.lcaohoanq.dto.response;

import com.lcaohoanq.dto.base.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailResponse extends Response {

    private String message;

}
