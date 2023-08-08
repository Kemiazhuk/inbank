package com.kem.inbank.services;

import com.kem.inbank.dto.DecisionStatus;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import com.kem.inbank.entity.Segments;
import com.kem.inbank.repositories.jpa.AccountJpaRepository;
import com.kem.inbank.services.strategy.CreditScoreMoreThenOneStrategy;
import com.kem.inbank.services.strategy.CreditScoreNotGreaterOneStrategy;
import com.kem.inbank.services.strategy.DecisionStrategy;
import com.kem.inbank.services.strategy.DecreaseCreditAmountStrategy;
import com.kem.inbank.services.strategy.IncreaseMonthPeriodStrategy;
import com.kem.inbank.services.strategy.RejectDecisionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CalculationDecisionServicesTests {
    @Mock
    private AccountJpaRepository accountJpaRepository;
    @Mock
    private CreditScoreMoreThenOneStrategy creditScoreMoreThenOneStrategy;
    @Mock
    private CreditScoreNotGreaterOneStrategy creditScoreNotGreaterOneStrategy;
    @Mock
    private DecreaseCreditAmountStrategy decreaseCreditAmountStrategy;
    @Mock
    private IncreaseMonthPeriodStrategy increaseMonthPeriodStrategy;
    @Mock
    private RejectDecisionStrategy rejectDecisionStrategy;
    private List<DecisionStrategy> decisionStrategies;
    @InjectMocks
    private CalculationDecisionServices calculationDecisionServices;

    @BeforeEach
    public void init() {
        decisionStrategies = new ArrayList<>();
        decisionStrategies.add(rejectDecisionStrategy);
        decisionStrategies.add(creditScoreMoreThenOneStrategy);
        decisionStrategies.add(creditScoreNotGreaterOneStrategy);
        decisionStrategies.add(decreaseCreditAmountStrategy);
        decisionStrategies.add(increaseMonthPeriodStrategy);
        ReflectionTestUtils.setField(calculationDecisionServices, "decisionStrategies", decisionStrategies);
    }

    @Test
    public void testCalculation() {
        String clientId = "123545";
        BigDecimal requestAmount = new BigDecimal("5000");
        BigDecimal requestMonth = new BigDecimal(24);

        RequestDecision requestDecision = new RequestDecision(clientId, requestAmount, requestMonth);
        AccountEntity accountEntity = new AccountEntity(clientId, Segments.valueOf("SEGMENT_1"));


        BigDecimal expectedMonth = new BigDecimal(24);
        ResponseDecision decision = new ResponseDecision(requestAmount, expectedMonth, DecisionStatus.APPROVED);

        when(accountJpaRepository.findById(clientId)).thenReturn(accountEntity);
        when(creditScoreMoreThenOneStrategy.getDecision(requestDecision, accountEntity)).thenReturn(null);
        when(creditScoreNotGreaterOneStrategy.getDecision(requestDecision, accountEntity)).thenReturn(null);
        when(decreaseCreditAmountStrategy.getDecision(requestDecision, accountEntity)).thenReturn(null);
        when(rejectDecisionStrategy.getDecision(requestDecision, accountEntity)).thenReturn(null);
        when(increaseMonthPeriodStrategy.getDecision(requestDecision, accountEntity)).thenReturn(decision);

        ResponseDecision responseDecision = calculationDecisionServices.calculation(requestDecision);

        // Assert the result
        assertEquals(requestAmount, responseDecision.getAmount());
        assertEquals(expectedMonth, responseDecision.getPeriod());
        Mockito.verify(creditScoreMoreThenOneStrategy).getDecision(requestDecision, accountEntity);
        Mockito.verify(creditScoreNotGreaterOneStrategy).getDecision(requestDecision, accountEntity);
        Mockito.verify(decreaseCreditAmountStrategy).getDecision(requestDecision, accountEntity);
        Mockito.verify(rejectDecisionStrategy).getDecision(requestDecision, accountEntity);
        Mockito.verify(increaseMonthPeriodStrategy).getDecision(requestDecision, accountEntity);
    }

    @Test
    public void testCalculationWithInvalidData() {
        String clientId = "123545";
        BigDecimal requestAmount = new BigDecimal("8000");
        BigDecimal requestMonth = new BigDecimal(24);
        AccountEntity accountEntity = new AccountEntity(clientId, Segments.valueOf("DEBT"));
        RequestDecision requestDecision = new RequestDecision(clientId, requestAmount, requestMonth);

        ResponseDecision expectedResponse = new ResponseDecision(BigDecimal.ZERO,BigDecimal.ZERO, DecisionStatus.DEBP);
        when(accountJpaRepository.findById(clientId)).thenReturn(accountEntity);
        when(rejectDecisionStrategy.getDecision(requestDecision, accountEntity)).thenReturn(expectedResponse);

        ResponseDecision responseDecision = calculationDecisionServices.calculation(requestDecision);

        // Assert the result
        BigDecimal expectedCreditAmount = BigDecimal.ZERO;
        assertEquals(expectedCreditAmount, responseDecision.getAmount());
        assertEquals(BigDecimal.ZERO, responseDecision.getPeriod());
        Mockito.verify(rejectDecisionStrategy).getDecision(requestDecision, accountEntity);
        Mockito.verify(decreaseCreditAmountStrategy,never()).getDecision(requestDecision, accountEntity);
    }
}
