package com.kem.inbank.services.controllers;

import com.kem.inbank.controllers.DecisionController;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.services.CalculationDecisionServices;
import com.kem.inbank.validators.RequestDecisionValidation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static com.kem.inbank.validators.RequestDecisionValidation.MAX_AMOUNT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(DecisionController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DecisionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void badRequest() throws Exception {
        this.mockMvc.perform(post("/getDecision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientId\": \"client_id\", \"amount\": 15000, \"month\": 50}"))
                .andExpect(status().isBadRequest());
    }
}
