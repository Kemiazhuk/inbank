package com.kem.inbank.services.strategy;

import com.kem.inbank.dto.DecisionStatus;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.kem.inbank.validators.RequestDecisionValidation.MIN_AMOUNT;

@Component
@Order(3)
public class CreditScoreNotGreaterOneStrategy implements DecisionStrategy {
    @Override
    public ResponseDecision getDecision(RequestDecision requestDecision, AccountEntity accountEntity) {
        BigDecimal segment = new BigDecimal(accountEntity.getSegment().getCreditModifier());
        if (segment.multiply(requestDecision.getMonth()).compareTo(MIN_AMOUNT) >= 0) {
            return new ResponseDecision(
                    segment.multiply(requestDecision.getMonth()),
                    requestDecision.getMonth(),
                    DecisionStatus.APPROVED);
        }
        return null;
    }
}
