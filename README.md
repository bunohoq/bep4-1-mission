# back

Spring Boot 기반의 멤버/게시글 도메인을 다루는 예제 백엔드 서비스입니다. JPA/H2를 사용하며, 도메인 이벤트를 통해 멤버/게시글 간 동기화와 활동 점수 갱신을 처리합니다.

## 주요 기능
- **멤버 관리**: 회원 생성, 활동 점수 증가 규칙 관리
- **게시글/댓글 관리**: 게시글 작성 및 댓글 추가
- **도메인 이벤트**
  - 게시글/댓글 생성 시 멤버 활동 점수 증가
  - 멤버 생성/수정 시 게시글 도메인에 멤버 복제 데이터 동기화
- **초기 데이터 로딩**: 애플리케이션 시작 시 샘플 멤버/게시글/댓글 생성

## 기술 스택
- Java 25 (Gradle Toolchain)
- Spring Boot 4.0.1
- Spring Data JPA
- H2 Database (파일 기반)
- Lombok

## 디렉터리 구조
```
src/main/java/com/back
├── boundedContext
│   ├── member
│   │   ├── app (Facade/UseCase)
│   │   ├── domain (Member, 정책)
│   │   ├── in (Controller, 이벤트 리스너, 초기 데이터)
│   │   └── out (Repository)
│   └── post
│       ├── app (Facade/UseCase)
│       ├── domain (Post, Comment, PostMember)
│       ├── in (이벤트 리스너, 초기 데이터)
│       └── out (Repository)
├── global (공통 유틸/설정/이벤트 발행)
└── shared (공유 DTO/도메인/이벤트)
```

## 실행 방법
### 1) 개발 환경 실행
```bash
./gradlew bootRun
```

### 2) 테스트 실행
```bash
./gradlew test
```

## 설정
- **기본 포트**: `8080`
- **프로파일**: `dev`
- **DB**: H2 파일 기반 (`jdbc:h2:./db_dev;MODE=MySQL`)

설정 파일:
- `src/main/resources/application.yml`
- `src/main/resources/application-dev.yml`

## 대표 API
- `GET /member/api/v1/members/randomSecureTip`
  - 멤버 정책에 기반한 보안 팁 메시지를 반환합니다.

## 동작 흐름 요약
- 회원 가입 → `MemberJoinedEvent` 발행 → Post 도메인에 `PostMember` 동기화
- 게시글/댓글 생성 → 이벤트 발행 → 멤버 활동 점수 증가
- 시작 시 초기 데이터가 자동 생성됩니다 (`MemberDataInit`, `PostDataInit`).
