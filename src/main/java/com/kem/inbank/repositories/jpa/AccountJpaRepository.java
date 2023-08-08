package com.kem.inbank.repositories.jpa;

import com.kem.inbank.entity.AccountEntity;
import com.kem.inbank.entity.Segments;
import com.kem.inbank.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountJpaRepository implements AccountRepository {
    @Override
    public AccountEntity findById(String id) {
        if (id.equals("49002010965")) {
            return new AccountEntity("49002010965", Segments.valueOf("DEBT"));
        }
        if (id.equals("49002010976")) {
            return new AccountEntity("49002010976", Segments.valueOf("SEGMENT_1"));
        }
        if (id.equals("49002010987")) {
            return new AccountEntity("49002010987", Segments.valueOf("SEGMENT_2"));
        }
        if (id.equals("49002010998")) {
            return new AccountEntity("49002010998", Segments.valueOf("SEGMENT_3"));
        }
        throw new IllegalArgumentException("Account not found");
    }
}
