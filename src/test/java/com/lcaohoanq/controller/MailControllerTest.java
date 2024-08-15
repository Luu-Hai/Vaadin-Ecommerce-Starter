package com.lcaohoanq.controller;

import com.lcaohoanq.constant.ApiConstant;
import com.lcaohoanq.entity.Status;
import com.lcaohoanq.entity.User;
import com.lcaohoanq.enums.UserStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "v1API=/api/v1")
@AutoConfigureMockMvc
@Disabled
public class MailControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private User mockUser;
    private User mockUserVerified;
    private User mockUserBanned;
    private String apiSentOtp = "/otp/send";
    private String apiForgotPassword = "/otp/send";
    private String apiBlockAccount = "/otp/send";

    @BeforeEach
    public void setUp() {
        mockUser = new User();
        mockUser.setFirstName("hoang");
        mockUser.setStatus(new Status(0, UserStatusEnum.VERIFIED));

        mockUserVerified = new User();
        mockUserVerified.setFirstName("hoang");
        mockUserVerified.setStatus(new Status(1, UserStatusEnum.VERIFIED));

        mockUserBanned = new User();
        mockUserBanned.setFirstName("hoang");
        mockUserBanned.setStatus(new Status(2, UserStatusEnum.BANNED));
    }

    private RequestPostProcessor userRequestPostProcessor() {
        return request -> {
            request.setAttribute("validatedEmail", mockUser);
            return request;
        };
    }

    @Test
    void testSendForgotPassword() throws Exception {
        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiForgotPassword)
                .param("type", "mail")
                .param("recipient", "hoangdz1604@gmail.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Mail sent successfully"));
    }

    @Test
    void testSendBlockAccount() throws Exception {
        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiBlockAccount)
                .param("type", "mail")
                .param("recipient", "hoangdz1604@gmail.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Mail sent successfully"));
    }

    @Test
    void testSendOtp() throws Exception {
        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiSentOtp)
                .param("type", "mail")
                .param("recipient", "hoangdz1604@gmail.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Mail sent successfully"));
    }

    @Test
    void testSendOtpToAccountNotExist() throws Exception {
        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiSentOtp)
                .param("type", "mail")
                .param("recipient", "abc@gmail.com"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Could not find userabc@gmail.com"));
    }

    @Test
    void testForgotPasswordToAccountNotExist() throws Exception {
        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiForgotPassword)
                .param("type", "mail")
                .param("recipient", "abc@gmail.com"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Could not find userabc@gmail.com"));
    }

    @Test
    void testSendBlockAccountToAccountNotExist() throws Exception {
        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiBlockAccount)
                .param("type", "mail")
                .param("recipient", "abc@gmail.com"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Could not find userabc@gmail.com"));
    }

//    @Test
//    void testSendForgotPasswordBanned() throws Exception {
//        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiForgotPassword)
//                .param("type", "mail")
//                .param("recipient", "hoangdz1604@gmail.com"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value("Mail sent successfully"));
//    }
//
//    @Test
//    void testSendBlockAccountBanned() throws Exception {
//        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiBlockAccount)
//                .param("type", "mail")
//                .param("recipient", "hoangdz1604@gmail.com"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value("Mail sent successfully"));
//    }
//
//    @Test
//    void testSendOtpBanned() throws Exception {
//        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiSentOtp)
//                .param("type", "mail")
//                .param("recipient", "hoangdz1604@gmail.com"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value("Mail sent successfully"));
//    }
//
//    @Test
//    void testSendOtpVerified() throws Exception {
//        mockMvc.perform(get(ApiConstant.INSTANCE.getBASE_URL_BE() +  apiSentOtp)
//                .param("type", "mail")
//                .param("recipient", "hoangdz1604@gmail.com"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value("Mail sent successfully"));
//    }
}
