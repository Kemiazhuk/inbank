package com.kem.inbank.services.strategy;

import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import com.kem.inbank.entity.Segments;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditScoreMoreThenOneStrategyTest {

    private final CreditScoreMoreThenOneStrategy creditScoreMoreThenOneStrategy = new CreditScoreMoreThenOneStrategy();

    @Test
    public void testCreditScoreMoreThenOneStrategy() {
        BigDecimal requestAmount = new BigDecimal("2500");
        BigDecimal segmentValue = new BigDecimal(Segments.valueOf("SEGMENT_1").getCreditModifier());
        BigDecimal requestMonth = new BigDecimal(45);
        String clientId = "123545";

        RequestDecision requestDecision = new RequestDecision(clientId, requestAmount, requestMonth);
        AccountEntity accountEntity = new AccountEntity(clientId, Segments.valueOf("SEGMENT_1"));
        ResponseDecision trueResponseDecision = creditScoreMoreThenOneStrategy.getDecision(requestDecision, accountEntity);

        BigDecimal expectedCreditAmount = segmentValue.multiply(requestMonth);

        assertEquals(expectedCreditAmount, trueResponseDecision.getAmount());
        assertEquals(requestMonth, trueResponseDecision.getPeriod());
    }
}
