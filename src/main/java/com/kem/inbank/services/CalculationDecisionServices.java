package com.kem.inbank.services;

import com.kem.inbank.dto.DecisionStatus;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.dto.ResponseDecision;
import com.kem.inbank.entity.AccountEntity;
import com.kem.inbank.repositories.AccountRepository;
import com.kem.inbank.services.strategy.DecisionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculationDecisionServices {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private List<DecisionStrategy> decisionStrategies;

    public ResponseDecision calculation(RequestDecision requestDecision) {
        AccountEntity accountEntity = accountRepository.findById(requestDecision.getClientId());

        for (DecisionStrategy strategy : decisionStrategies) {
            ResponseDecision responseDecision = strategy.getDecision(requestDecision, accountEntity);
            if (responseDecision != null) {
                return responseDecision;
            }
        }
        return new ResponseDecision(BigDecimal.ZERO, BigDecimal.ZERO, DecisionStatus.DINIED);
    }
}
