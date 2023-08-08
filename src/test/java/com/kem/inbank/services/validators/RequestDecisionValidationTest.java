package com.kem.inbank.services.validators;

import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.validators.RequestDecisionValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RequestDecisionValidationTest {

    private final RequestDecisionValidation requestDecisionValidation = new RequestDecisionValidation();

    @Test
    public void testValidRange() {
        BigDecimal requestAmount = new BigDecimal(4500);
        BigDecimal requestMonth = new BigDecimal(45);
        String clientId = "111-222-333";
        RequestDecision requestDecision = new RequestDecision(clientId, requestAmount, requestMonth);
        Map<String, BigDecimal> resultValidation = requestDecisionValidation.validate(requestDecision);

        assertEquals(0, resultValidation.size());
    }

    @Test
    public void testInvalidRange() {
        BigDecimal requestAmount = new BigDecimal(4500);
        BigDecimal requestMonth = new BigDecimal(67);
        String clientId = "111-222-333";
        RequestDecision requestDecision = new RequestDecision(clientId, requestAmount, requestMonth);
        Map<String, BigDecimal> resultValidation = requestDecisionValidation.validate(requestDecision);

        assertEquals(1, resultValidation.size());
        assertEquals(requestMonth, resultValidation.get("month"));

        requestAmount = new BigDecimal(1500);
        requestDecision.setAmount(requestAmount);
        resultValidation = requestDecisionValidation.validate(requestDecision);

        assertEquals(2, resultValidation.size());
        assertEquals(requestAmount, resultValidation.get("amount"));
        assertEquals(requestMonth, resultValidation.get("month"));

        requestMonth = new BigDecimal(50);
        requestDecision.setMonth(requestMonth);
        resultValidation = requestDecisionValidation.validate(requestDecision);

        assertEquals(1, resultValidation.size());
        assertEquals(requestAmount, resultValidation.get("amount"));

        assertNull(requestDecisionValidation.validate(null));
    }
}
