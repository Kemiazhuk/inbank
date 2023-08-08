package com.kem.inbank.controllers;

import com.kem.inbank.dto.InvalidResponseDecision;
import com.kem.inbank.dto.RequestDecision;
import com.kem.inbank.services.CalculationDecisionServices;
import com.kem.inbank.validators.RequestDecisionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DecisionController {
    @Autowired
    private CalculationDecisionServices calculationDecisionServices;
    @Autowired
    private RequestDecisionValidation requestDecisionValidation;

    @RequestMapping(value = "/getDecision", method = RequestMethod.POST)
    public ResponseEntity getDecision(@RequestBody RequestDecision requestDecision) {
        Map<String, BigDecimal> validationErrors = requestDecisionValidation.validate(requestDecision);
        if ((validationErrors != null) && (validationErrors.isEmpty())) {
            try {
                return new ResponseEntity<>(calculationDecisionServices.calculation(requestDecision), HttpStatus.OK);
            } catch (IllegalArgumentException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else if (validationErrors == null) {
            return new ResponseEntity("message: Request was empty", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(new InvalidResponseDecision(validationErrors), HttpStatus.BAD_REQUEST);
        }
    }

//    private static Map<String,String> getErrorMessage(Map<String, BigDecimal> validationErrors) {
//        StringBuilder sb = new StringBuilder("Wrong data in request ");
//        validationErrors.entrySet()
//                .forEach(entry -> sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" "));
//
//        return new HashMap<>("message", sb.toString());
//    }
}

