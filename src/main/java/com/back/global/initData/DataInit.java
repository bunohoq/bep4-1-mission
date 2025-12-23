package com.back.global.initData;


import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;

import com.back.boundedContext.post.app.PostWriteUseCase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Slf4j
public class DataInit {
    private final DataInit self;
    private final MemberFacade memberFacade;
    private final PostWriteUseCase postWriteUseCase;

    public DataInit(@Lazy DataInit self, MemberFacade memberFacade, PostWriteUseCase postWriteUseCase) {
        this.self = self;
        this.memberFacade = memberFacade;
        this.postWriteUseCase = postWriteUseCase;
    }

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            self.makeBaseMembers();
            self.makeBasePosts();
            self.makeBasePostComments();
        };
    }

    @Transactional
    public void makeBasePostComments() {
        Post post1 = postWriteUseCase.findById(1).get();
        Post post2 = postWriteUseCase.findById(2).get();
        Post post3 = postWriteUseCase.findById(3).get();
        Post post4 = postWriteUseCase.findById(4).get();
        Post post5 = postWriteUseCase.findById(5).get();
        Post post6 = postWriteUseCase.findById(6).get();

        Member user1Member = memberFacade.findByUsername("user1").get();
        Member user2Member = memberFacade.findByUsername("user2").get();
        Member user3Member = memberFacade.findByUsername("user3").get();

        if (post1.hasComments()) return;

        post1.addComment(user1Member, "댓글1");
        post1.addComment(user2Member, "댓글2");
        post1.addComment(user3Member, "댓글3");

        post2.addComment(user2Member, "댓글4");
        post2.addComment(user2Member, "댓글5");

        post3.addComment(user3Member, "댓글6");
        post3.addComment(user3Member, "댓글7");

        post4.addComment(user1Member, "댓글8");
    }

    @Transactional
    public void makeBasePosts(){
        if(postWriteUseCase.count()>0) return;
        Member user1Member = memberFacade.findByUsername("user1").get();
        Member user2Member = memberFacade.findByUsername("user2").get();
        Member user3Member = memberFacade.findByUsername("user3").get();

        Post post1 = postWriteUseCase.write(user1Member, "제목1", "내용1");
        Post post2 = postWriteUseCase.write(user1Member, "제목2", "내용2");
        Post post3 = postWriteUseCase.write(user1Member, "제목3", "내용3");
        Post post4 = postWriteUseCase.write(user2Member, "제목4", "내용4");
        Post post5 = postWriteUseCase.write(user2Member, "제목5", "내용5");
        Post post6 = postWriteUseCase.write(user3Member, "제목6", "내용6");
    }

    @Transactional
    public void makeBaseMembers() {
        if (memberFacade.count() > 0) return; //회원 데이터가 없을때에만 아래 코드가 실행되게끔

        Member systemMember = memberFacade.join("system", "1234", "시스템");
        Member holdingMember = memberFacade.join("holding", "1234", "홀딩");
        Member adminMember = memberFacade.join("admin", "1234", "관리자");
        Member user1Member = memberFacade.join("user1", "1234", "유저1");
        Member user2Member = memberFacade.join("user2", "1234", "유저2");
        Member user3Member = memberFacade.join("user3", "1234", "유저3");
    }
}