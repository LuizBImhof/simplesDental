package com.simplesdental.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.product.model.authentication.RegisterRequest;
import com.simplesdental.product.service.AuthService;
import com.simplesdental.product.Utils.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "teste@teste.com", roles = "USER")
    @Test
    void shouldReturnUserContext() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/context"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegisterNewUser() throws Exception {
        RegisterRequest request = new RegisterRequest("Novo Usuário", "novo@teste.com", "Password123", Role.USER);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
