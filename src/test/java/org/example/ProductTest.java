package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.authentication.application.impl.AuthServiceImpl;
import org.example.authentication.dto.RequestLoginDTO;
import org.example.authentication.dto.RequestRegistrationDTO;
import org.example.authentication.dto.ResponseLoginDTO;
import org.example.product.dto.RequestProductDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {
    private static final Logger logger = LoggerFactory.getLogger(ProductTest.class);

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void RegistrationUsers(){
        authService.register(new RequestRegistrationDTO("username1", "password", "sector"));
        authService.register(new RequestRegistrationDTO("username2", "password", "sector"));
        authService.register(new RequestRegistrationDTO("username3", "password", "sector"));
    }

    @Test
    public void productAuthenticationToReadAllWithJwtAbort() throws Exception {
        logger.warn("AuthenticationToAll(abort) started!");
        mvc.perform(get("/api/product")).andExpect(status().isUnauthorized());
        logger.warn("Authentication(abort) ended!");
    }

    @Test
    public void productAuthenticationToReadAllWithJwtSuccess() throws Exception {
        logger.warn("AuthenticationToAll(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(get("/api/product")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
        logger.warn("AuthenticationToAll(success) ended!");
    }

    @Test
    public void productAuthenticationToReadOneWithJwtAbort() throws Exception {
        logger.warn("AuthenticationToOne(abort) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(get("/api/product/3")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isUnauthorized());
        logger.warn("AuthenticationToOne(abort) ended!");
    }

    @Test
    public void productAuthenticationToReadOneWithJwtSuccess() throws Exception {
        logger.warn("AuthenticationToOne(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(get("/api/product/3")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
        logger.warn("AuthenticationToOne(success) ended!");
    }

    @Test
    public void productAuthenticationToCreateWithJwtAbort() throws Exception {
        logger.warn("AuthenticationToCreate(abort) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(post("/api/product")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RequestProductDTO(
                                "TestNameAborted",
                                15,
                                15.5,
                                3L))
                        ))
                .andExpect(status().isUnauthorized());
        logger.warn("AuthenticationToCreate(abort) ended!");
    }

    @Test
    public void productAuthenticationToCreateSuccessWithJwt() throws Exception {
        logger.warn("AuthenticationToCreate(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(post("/api/product")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RequestProductDTO("TestName", 15, 15.5, 3L))))
                .andExpect(status().isOk());
        logger.warn("AuthenticationToCreate(success) ended!");
    }

    @Test
    public void productAuthenticationToDeleteWithJwtAbort() throws Exception {
        logger.warn("AuthenticationToDelete(abort) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(delete("/api/product/5")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isUnauthorized());
        logger.warn("AuthenticationToDelete(abort) ended!");
    }

    @Test
    public void productAuthenticationToDeleteSuccessWithJwt() throws Exception {
        logger.warn("AuthenticationToDelete(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(delete("/api/product/5")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
        logger.warn("AuthenticationToDelete(success) ended!");
    }

    @Test
    public void productAuthenticationToUpdateAbortWithJwt() throws Exception {
        logger.warn("AuthenticationToUpdate(abort) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(put("/api/product/4")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new RequestProductDTO("PenisGambling", 10, 5.0, 3L))))
                .andExpect(status().isUnauthorized());
        logger.warn("AuthenticationToUpdate(abort) ended!");
    }

    @Test
    public void productAuthenticationToUpdateSuccessWithJwt() throws Exception {
        logger.warn("AuthenticationToUpdate(success) started!");
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(put("/api/product/4")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RequestProductDTO("PenisGambling", 10, 5.0, 3L))))
                .andExpect(status().isOk());
        logger.warn("AuthenticationToUpdate(success) ended!");
    }

    private String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
