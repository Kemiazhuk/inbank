package com.kem.inbank.services.strategy;

import com.kem.inbank.dto.DecisionStatus;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.kem.inbank.validators.RequestDecisionValidation.MAX_MONTH;

@Component
@Order(1)
public class RejectDecisionStrategy implements DecisionStrategy {
    @Override
    public ResponseDecision getDecision(RequestDecision requestDecision, AccountEntity accountEntity) {
        if (accountEntity.getSegment() != null && accountEntity.getSegment().getCreditModifier() != 0) {
            BigDecimal segment = BigDecimal.valueOf(accountEntity.getSegment().getCreditModifier());
            if (requestDecision.getAmount().divide(segment, 10, RoundingMode.HALF_UP).compareTo(MAX_MONTH) > 0) {
                return new ResponseDecision(BigDecimal.ZERO, BigDecimal.ZERO, DecisionStatus.DINIED);
            }
        } else if (accountEntity.getSegment().getCreditModifier() == 0) {
            return new ResponseDecision(BigDecimal.ZERO, BigDecimal.ZERO, DecisionStatus.DEBP);
        }
        return null;
    }
}
