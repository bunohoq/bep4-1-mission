package com.back.shared.cash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CashMemberCreatedEvent {
    private final CashMemberDto member;
}
