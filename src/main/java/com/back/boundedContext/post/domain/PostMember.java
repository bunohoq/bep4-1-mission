package com.back.boundedContext.post.domain;

import com.back.shared.member.domain.RepliceMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_MEMBER")
@Getter
@NoArgsConstructor
public class PostMember extends RepliceMember {
    public PostMember(String username, String password, String nickname) {
        super(username, password, nickname);
    }
}
