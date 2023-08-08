package com.kem.inbank.services.strategy;

import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import org.springframework.stereotype.Service;

@Service
public interface DecisionStrategy {
    ResponseDecision getDecision(RequestDecision requestDecision, AccountEntity accountEntity);
}
