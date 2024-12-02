package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.authentication.application.AuthService;
import org.example.authentication.dto.RequestLoginDTO;
import org.example.authentication.dto.ResponseLoginDTO;
import org.example.authentication.enums.Role;
import org.example.customer.dto.RequestCustomerDTO;
import org.example.user.dto.RequestUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void userAuthenticationToCreateWithJwtAbort() throws Exception {
        logger.warn("AuthenticationCreate(abort) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(post("/api/user")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(
                        new RequestUserDTO(
                                "newUser",
                                "newPassword",
                                Role.USER,
                                new RequestCustomerDTO("newCustomer", "newSector", null)
                        ))))
                .andExpect(status().isUnauthorized());
        logger.warn("AuthenticationCreate(abort) ended!");
    }

    @Test
    public void userAuthenticationToCreateWithJwtSuccess() throws Exception {
        logger.warn("AuthenticationCreate(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username2", "password"));
        mvc.perform(post("/api/user")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                new RequestUserDTO(
                                        "newUsername",
                                        "newPassword",
                                        Role.USER,
                                        new RequestCustomerDTO("newCustomer", "newSector", null)
                                ))))
                .andExpect(status().isOk());
        logger.warn("AuthenticationCreate(success) ended!");
    }

    @Test
    public void userAuthenticationToReadWithRoleManagerJwtAbort() throws Exception {
        logger.warn("AuthenticationReadWithRoleManager(abort) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(get("/api/user/3")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isUnauthorized());
        logger.warn("AuthenticationReadWithRoleManager(abort) ended!");
    }

    @Test
    public void userAuthenticationToReadWithRoleUserJwtSuccess() throws Exception {
        logger.warn("AuthenticationReadWithRoleUser(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(get("/api/user/3")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
        logger.warn("AuthenticationReadWithRoleUser(success) ended!");
    }

    @Test
    public void userAuthenticationToReadWithRoleUserJwtAbort() throws Exception {
        logger.warn("AuthenticationReadWithRoleUser(abort) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(get("/api/user/4")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isUnauthorized());
        logger.warn("AuthenticationReadWithRoleUser(abort) ended!");
    }

    @Test
    public void userAuthenticationToReadWithRoleAdminJwtSuccess() throws Exception {
        logger.warn("AuthenticationReadWithUserAdmin(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username2", "password"));
        mvc.perform(get("/api/user/3")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
        logger.warn("AuthenticationReadWithRoleAdmin(success) ended!");
    }

    @Test
    public void userAuthenticationToDeleteWithRoleUserJwtSuccess() throws Exception {
        logger.warn("AuthenticationDeleteWithUserAdmin(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(delete("/api/user/3")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
        logger.warn("AuthenticationDeleteWithUserAdmin(success) ended!");
    }

    @Test
    public void userAuthenticationToUpdateWithRoleUserJwtSuccess() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(put("/api/user/12")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RequestUserDTO("newusernmae", "newpassword", Role.USER, null))))
                .andExpect(status().isOk());
    }

    private String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
