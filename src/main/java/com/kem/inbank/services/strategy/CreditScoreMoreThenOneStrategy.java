package com.kem.inbank.services.strategy;

import com.kem.inbank.dto.DecisionStatus;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.kem.inbank.validators.RequestDecisionValidation.MAX_AMOUNT;

@Component
@Order(2)
public class CreditScoreMoreThenOneStrategy implements DecisionStrategy {
    @Override
    public ResponseDecision getDecision(RequestDecision requestDecision, AccountEntity accountEntity) {
        BigDecimal segment = new BigDecimal(accountEntity.getSegment().getCreditModifier());
        BigDecimal creditScore = segment
                .divide(requestDecision.getAmount(), 10, RoundingMode.HALF_UP)
                .multiply(requestDecision.getMonth());

        if (creditScore.compareTo(BigDecimal.ONE) > 0) {
            BigDecimal creditAmount = segment.multiply(requestDecision.getMonth());
            creditAmount = creditAmount.compareTo(MAX_AMOUNT) >= 0 ? MAX_AMOUNT : creditAmount;
            return new ResponseDecision(creditAmount, requestDecision.getMonth(), DecisionStatus.APPROVED);
        }
        return null;
    }
}
