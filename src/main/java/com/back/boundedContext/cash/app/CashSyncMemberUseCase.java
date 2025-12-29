package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {
    private final CashMemberRepository cashMemberRepository;
    private final EventPublisher eventPublisher;

    public CashMember syncMember(MemberDto memberDto) {
        boolean isNew = !cashMemberRepository.existsById(memberDto.getId());
        CashMember cashMember =cashMemberRepository.save(
                new CashMember(
                        memberDto.getId(),
                        memberDto.getCreateDate(),
                        memberDto.getModifyDate(),
                        memberDto.getUsername(),
                        "",
                        memberDto.getNickname(),
                        memberDto.getActivityScore()
                )
        );
        if(isNew) {
            eventPublisher.publish(new CashMemberCreatedEvent(new CashMemberDto(cashMember)));
        }
        return cashMember;
    }
}
