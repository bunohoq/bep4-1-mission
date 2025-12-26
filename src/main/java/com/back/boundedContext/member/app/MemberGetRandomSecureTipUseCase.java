package com.back.boundedContext.member.app;


import com.back.boundedContext.member.domain.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGetRandomSecureTipUseCase {
    private final MemberPolicy memberPolicy;

    public String getRandomSecureTip() {
        return "비민번호의 유효기간의 %d일 입니다."
                .formatted(memberPolicy, memberPolicy.getNeedToChangePasswordDays());
    }

}
