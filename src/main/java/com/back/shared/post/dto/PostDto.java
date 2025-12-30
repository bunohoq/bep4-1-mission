package com.back.shared.post.dto;

import com.back.boundedContext.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PostDto {
    // DTO에 final 붙이는걸 권장, 대신에 allargsconstructor에 옵션을 준다.
    // 직렬화 관련해서, final을 생략해야 하는 경우에...
    private final int id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final int authorId;
    private final String authorName;
    private final String title;
    private final String content;
}
