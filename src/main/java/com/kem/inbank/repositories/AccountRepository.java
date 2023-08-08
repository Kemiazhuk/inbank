package com.kem.inbank.repositories;

import com.kem.inbank.entity.AccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository {
    AccountEntity findById(String id);
}
