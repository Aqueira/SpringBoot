package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.authentication.application.impl.AuthServiceImpl;
import org.example.authentication.dto.RequestLoginDTO;
import org.example.authentication.dto.RequestRegistrationDTO;
import org.example.authentication.dto.ResponseLoginDTO;
import org.example.product.dto.RequestProductDTO;
import org.junit.jupiter.api.Test;
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
        mvc.perform(get("/api/product")).andExpect(status().isUnauthorized());
    }

    @Test
    public void productAuthenticationToReadAllWithJwtSuccess() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(get("/api/product")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void productAuthenticationToReadOneWithJwtAbort() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(get("/api/product/3")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void productAuthenticationToReadOneWithJwtSuccess() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(get("/api/product/3")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void productAuthenticationToCreateWithJwtAbort() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(post("/api/product")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RequestProductDTO("TestNameAborted", 15.5))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void productAuthenticationToCreateSuccessWithJwt() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(post("/api/product")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RequestProductDTO("TestNameAborted", 15.5))))
                .andExpect(status().isOk());
    }

    @Test
    public void productAuthenticationToDeleteWithJwtAbort() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(delete("/api/product/1")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void productAuthenticationToDeleteSuccessWithJwt() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(delete("/api/product/1")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void productAuthenticationToUpdateAbortWithJwt() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username3", "password"));
        mvc.perform(put("/api/product/4")
                .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new RequestProductDTO("PenisGambling", 15.0))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void productAuthenticationToUpdateSuccessWithJwt() throws Exception {
        ResponseLoginDTO responseLoginDTO = authService.login(new RequestLoginDTO("username1", "password"));
        mvc.perform(put("/api/product/2")
                        .header("Authorization", "Bearer " + responseLoginDTO.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new RequestProductDTO("PenisGambling", 15.0))))
                .andExpect(status().isOk());
    }

    private String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
