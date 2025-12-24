package com.back.boundedContext.post.app;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.EventPublisher.EventPublisher;
import com.back.global.rsData.RsData;
import com.back.shared.member.out.MemberApiClient;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {
    private final PostRepository postRepository; //주입
    private final EventPublisher eventPublisher;
    private final MemberApiClient memberApiClient;


    public long count() {
        return postRepository.count();
    }

    public RsData<Post> write(PostMember author, String title, String content) {
        Post post = postRepository.save(new Post (author, title, content));

        eventPublisher.publish(
                new PostCreatedEvent(
                        new PostDto(post)
                )
        );
        //author.increaseActivityScore(3);

        //return new RsData<>("201-1", "%d번 글이 생성되었습니다.".formatted(post.getId()), post);

        String randomSecurityTip = memberApiClient.getRandomSecurityTip();

        return new RsData<> (
                "201-1",
                "%d번 글이 생성되었습니다. 보안 팁 :%s"
                        .formatted(post.getId(), randomSecurityTip),
                post
        );
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }
}
