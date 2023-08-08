package com.kem.inbank.services.strategy;

import com.kem.inbank.dto.DecisionStatus;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Order(5)
public class IncreaseMonthPeriodStrategy implements DecisionStrategy {
    @Override
    public ResponseDecision getDecision(RequestDecision requestDecision, AccountEntity accountEntity) {
        BigDecimal segment = new BigDecimal(accountEntity.getSegment().getCreditModifier());
        return new ResponseDecision(
                requestDecision.getAmount(),
                requestDecision.getAmount().divide(segment, 10, RoundingMode.DOWN).setScale(0),
                DecisionStatus.APPROVED);
    }
}
