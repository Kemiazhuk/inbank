package com.kem.inbank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountEntity {
    private String id;
    private Segments segment;
}
