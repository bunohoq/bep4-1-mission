package com.back.boundedContext.cash.domain;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

// 회원의 현금 지갑
@Entity
@Table(name = "CASH_WALLET")
@NoArgsConstructor
@Getter
public class Wallet extends BaseManualIdAndTime {
    // 지갑 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    private CashMember holder;

    // 잔고
    @Getter
    private long balance;

    // 현금 사용 내역
    @OneToMany(mappedBy = "wallet", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<CashLog> cashLogs = new ArrayList<>();

    // 지갑 생성
    public Wallet(CashMember holder) {
        super(holder.getId());
        this.holder = holder;
    }

    // 잔고 확인
    public boolean hasBalance() {
        return balance > 0;
    }

    // 현금 충전
    public void credit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        balance += amount;
        addCashLog(amount, eventType, relTypeCode, relId);
    }

    public void credit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        credit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void credit(long amount, CashLog.EventType eventType) {
        credit(amount, eventType, holder);
    }

    // 현금 사용
    public void debit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        balance -= amount;

        addCashLog(-amount, eventType, relTypeCode, relId);
    }

    public void debit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void debit(long amount, CashLog.EventType eventType) {
        debit(amount, eventType, holder);
    }

    // 현금 사용 내역 기록
    private CashLog addCashLog(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        CashLog cashLog = new CashLog(
                eventType,
                relTypeCode,
                relId,
                holder,
                this,
                amount,
                balance
        );
        cashLogs.add(cashLog);
        return cashLog;
    }

}
