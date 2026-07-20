# instagram_toy

Spring Boot와 JPA를 사용해 인스타그램의 피드, 댓글, 좋아요 기능을 단순화한 토이 프로젝트입니다.

이번 개발의 목적은 완성된 서비스를 만드는 것이 아니라, 기본 CRUD 개발을 위한 도메인 모델링, JPA 연관관계 설계, 계층 구조, DTO 변환 스타일을 정리하는 것입니다.

## 개발 환경

- Java 17
- Spring Boot 4.1.0
- Spring Data JPA
- MySQL Driver
- Lombok
- Gradle

## 1단계: JPA 도메인 모델 설계

초기 요구사항으로는 Controller, Service, DTO 없이 Entity와 Repository만 구현하도록 했습니다.

구현 대상 엔티티는 다음과 같습니다.

- `User`
- `Post`
- `PostImage`
- `Comment`
- `PostLike`
- `CommentLike`

피드와 댓글 기능을 구성하기 위해 사용자, 게시글, 이미지, 댓글, 게시글 좋아요, 댓글 좋아요를 각각 독립된 엔티티로 설계했습니다.

### 공통 엔티티

공통 시간 필드를 관리하기 위해 공통 엔티티를 분리했습니다.

- `BaseEntity`
  - `id`
  - `createdAt`
  - `updatedAt`

- `CreatedAtEntity`
  - `id`
  - `createdAt`

게시글, 댓글, 이미지처럼 수정 시간이 필요한 엔티티는 `BaseEntity`를 사용하고, 좋아요처럼 생성 시간만 필요한 엔티티는 `CreatedAtEntity`를 사용하도록 했습니다.

## 2단계: 엔티티별 역할

### User

사용자 엔티티입니다.

- `id`
- `nickname`
- `createdAt`
- `updatedAt`

요구사항에 따라 이메일, 비밀번호, 프로필 이미지는 추가하지 않았습니다.

테이블명은 요구사항에서 `users`로 지정되어 있어 `@Table(name = "users")`를 유지했습니다.

### Post

게시글 엔티티입니다.

- 작성자: `User`
- 본문: `content`
- 이미지 목록
- 댓글 목록
- 좋아요 목록

`commentCount`, `likeCount`는 필드로 저장하지 않고, 조회 시 count 쿼리로 계산하도록 했습니다.

### PostImage

게시글 이미지 엔티티입니다.

- 게시글: `Post`
- S3 이미지 키: `imageKey`
- 표시 순서: `displayOrder`

이미지는 실제 S3 Presigned URL 방식으로 업로드된다고 가정했습니다. 다만 Presigned URL 발급, S3 업로드, S3 삭제 로직은 구현하지 않고, 업로드 완료 후 전달받은 `imageKey`와 `displayOrder`만 저장하도록 했습니다.

같은 게시글 안에서 이미지 표시 순서가 중복되지 않도록 `(post_id, display_order)` 유니크 제약조건을 설정했습니다.

### Comment

댓글과 대댓글을 함께 표현하는 엔티티입니다.

- 게시글: `Post`
- 작성자: `User`
- 부모 댓글: `parent`
- 내용: `content`
- 자식 댓글 목록
- 댓글 좋아요 목록

일반 댓글은 `parent`가 `null`이고, 대댓글은 `parent`에 부모 `Comment`를 저장하도록 했습니다.

대댓글은 1단계까지만 허용하는 구조를 전제로 했고, 실제 제한 검증은 Service 계층에서 처리하도록 했습니다.

### PostLike

게시글 좋아요 연결 엔티티입니다.

- 사용자: `User`
- 게시글: `Post`
- 생성 시간: `createdAt`

`User N:M Post` 관계는 `@ManyToMany`를 직접 사용하지 않고 `PostLike` 연결 엔티티로 해소했습니다.

좋아요 여부는 Boolean 필드로 저장하지 않고, 행의 존재 여부로 표현하도록 했습니다.

### CommentLike

댓글 좋아요 연결 엔티티입니다.

- 사용자: `User`
- 댓글: `Comment`
- 생성 시간: `createdAt`

`User N:M Comment` 관계도 `CommentLike` 연결 엔티티로 해소했습니다.

좋아요 여부는 `PostLike`와 동일하게 행의 존재 여부로 표현하도록 했습니다.

## 3단계: JPA 연관관계 기준

연관관계는 무조건 추가하지 않고 다음 기준으로 설계했습니다.

- 같은 도메인 안에서 객체 단위로 함께 다룰 필요가 있으면 JPA 연관관계 사용
- 단순히 식별자만 알면 충분한 경우에는 ID 필드 저장 방식도 고려
- DB 외래 키가 존재한다고 해서 반드시 JPA 연관관계를 만들지는 않음
- 기본 로딩 전략은 `LAZY`
- `@ManyToMany`는 직접 사용하지 않음

이번 프로젝트의 `User`, `Post`, `PostImage`, `Comment`, `PostLike`, `CommentLike`는 피드, 댓글, 좋아요라는 하나의 도메인 안에서 함께 다룰 필요가 있으므로 JPA 연관관계를 사용하도록 했습니다.

## 4단계: 양방향 연관관계 기준

양방향 연관관계는 다음 관계에 적용했습니다.

- `Post` -> `PostImage`
- `Post` -> `Comment`
- `Post` -> `PostLike`
- `Comment` -> 자식 `Comment`
- `Comment` -> `CommentLike`

양방향 연관관계를 둔 주된 이유는 조회 편의가 아니라 삭제 정책을 표현하기 위해서입니다.

- 게시글 삭제 시 이미지 삭제
- 게시글 삭제 시 댓글과 대댓글 삭제
- 게시글 삭제 시 게시글 좋아요 삭제
- 댓글 삭제 시 댓글 좋아요 삭제
- 부모 댓글 삭제 시 대댓글 삭제

위 정책을 JPA cascade와 orphanRemoval로 처리하기 위해 부모 엔티티 쪽에 컬렉션을 두었습니다.

다만 모든 외래 키 관계를 양방향으로 만들지는 않고, 삭제 생명주기를 부모 객체에서 관리해야 하는 관계를 중심으로 양방향을 사용하도록 했습니다.

## 5단계: 테이블 어노테이션 정리

테이블 어노테이션은 모든 엔티티에 일괄적으로 사용하지 않고, 필요한 경우에만 사용하도록 정리했습니다.

현재 `@Table`을 유지한 곳은 다음과 같습니다.

- `User`: 요구사항상 테이블명이 `users`
- `PostImage`: `(post_id, display_order)` 유니크 제약조건 필요
- `PostLike`: `(user_id, post_id)` 유니크 제약조건 필요
- `CommentLike`: `(user_id, comment_id)` 유니크 제약조건 필요

단순 테이블명 지정만을 위한 `@Table`은 제거하고, 테이블명 또는 제약조건 설정이 필요한 경우에만 유지하도록 했습니다.

## 6단계: 유니크 제약조건

유니크 제약조건은 DB 레벨에서 중복 데이터를 막기 위해 사용했습니다.

### PostImage

```text
(post_id, display_order)
```

같은 게시글 안에서 이미지 순서가 중복되지 않도록 설정했습니다.

다른 게시글에서는 같은 `displayOrder`를 사용할 수 있으므로 `display_order` 하나만 유니크로 두지 않고, `post_id`와 함께 복합 유니크 제약조건을 사용했습니다.

### PostLike

```text
(user_id, post_id)
```

한 사용자가 같은 게시글에 좋아요를 중복으로 남길 수 없도록 설정했습니다.

### CommentLike

```text
(user_id, comment_id)
```

한 사용자가 같은 댓글에 좋아요를 중복으로 남길 수 없도록 설정했습니다.

Service에서 중복 여부를 확인하더라도 동시에 요청이 들어올 수 있으므로, 최종 데이터 무결성은 DB 제약조건으로 보장하도록 했습니다.

## 7단계: 패키지 구조 변경

패키지 구조는 도메인별 상위 폴더를 만들고, 그 안에 계층 폴더를 두는 방식으로 정리했습니다.

현재 구조는 다음과 같습니다.

```text
com.example.instagram
├── common
│   ├── entity
│   └── util
├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
├── post
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
├── postimage
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
├── comment
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
├── postlike
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
└── commentlike
    ├── controller
    ├── dto
    ├── entity
    ├── repository
    └── service
```

Controller와 Service는 먼저 기본 어노테이션만 적용한 빈 클래스로 생성한 뒤, API 구현 단계에서 필요한 메서드를 추가했습니다.

- Controller
  - `@RestController`
  - `@RequiredArgsConstructor`
  - `@RequestMapping`

- Service
  - `@Service`
  - `@RequiredArgsConstructor`

## 8단계: Repository 구현

각 엔티티별 Repository는 `JpaRepository`를 상속하도록 구현했습니다.

- `UserRepository`
- `PostRepository`
- `PostImageRepository`
- `CommentRepository`
- `PostLikeRepository`
- `CommentLikeRepository`

API 구현에 필요한 조회 메서드는 Repository에 추가했습니다.

### PostRepository

- `findAllByOrderByCreatedAtDesc()`

게시글 전체 조회를 최신순으로 처리하기 위해 사용합니다.

### PostImageRepository

- `findByPostIdOrderByDisplayOrderAsc(Long postId)`

특정 게시글의 이미지를 표시 순서대로 조회하기 위해 사용합니다.

### CommentRepository

- `findByPostIdAndParentIsNullOrderByCreatedAtAsc(Long postId)`
- `findByParentIdOrderByCreatedAtAsc(Long parentId)`
- `countByPostId(Long postId)`
- `countByParentId(Long parentId)`

최상위 댓글 조회, 대댓글 조회, 게시글 댓글 수 조회, 대댓글 수 조회에 사용합니다.

### PostLikeRepository

- `findByUserIdAndPostId(Long userId, Long postId)`
- `existsByUserIdAndPostId(Long userId, Long postId)`
- `countByPostId(Long postId)`

게시글 좋아요 토글과 좋아요 수 조회에 사용합니다.

### CommentLikeRepository

- `findByUserIdAndCommentId(Long userId, Long commentId)`
- `existsByUserIdAndCommentId(Long userId, Long commentId)`
- `countByCommentId(Long commentId)`

댓글 좋아요 토글과 좋아요 수 조회에 사용합니다.

## 9단계: API 구현 범위

Entity와 Repository 구현 다음 단계에서는 요구된 API만 Controller, Service, Request DTO, Response DTO로 구현하도록 했습니다.

인증과 인가는 구현하지 않고, 사용자 식별이 필요한 경우 요청값으로 `userId` 또는 `authorId`를 직접 받도록 했습니다.

Entity를 직접 반환하지 않고 DTO를 반환하도록 했습니다.

### Post API

- `POST /api/posts`
  - 게시글 작성
  - `authorId`, `content`, `images`를 받음
  - 이미지는 업로드 완료 후 전달받은 `imageKey`, `displayOrder`만 저장

- `GET /api/posts/{postId}`
  - 게시글 단건 조회
  - 작성자, 본문, 이미지, 좋아요 수, 댓글 수, 생성/수정 시간, 경과 시간을 반환

- `GET /api/posts`
  - 게시글 전체 조회
  - 최신순 조회

- `PATCH /api/posts/{postId}`
  - 게시글 수정
  - `content`만 수정
  - 요청한 `userId`와 게시글 작성자 ID가 같아야 함

- `DELETE /api/posts/{postId}`
  - 게시글 삭제
  - 요청한 `userId`와 게시글 작성자 ID가 같아야 함

### Comment API

- `POST /api/posts/{postId}/comments`
  - 댓글 작성

- `GET /api/posts/{postId}/comments`
  - 게시글의 최상위 댓글만 조회

- `POST /api/comments/{commentId}/replies`
  - 대댓글 작성
  - 부모 댓글이 일반 댓글인 경우에만 허용
  - 대댓글에는 추가 대댓글 작성 불가

- `GET /api/comments/{commentId}/replies`
  - 특정 댓글의 대댓글 전체 조회

- `PATCH /api/comments/{commentId}`
  - 댓글 또는 대댓글 수정
  - `content`만 수정
  - 작성자 ID 검증

- `DELETE /api/comments/{commentId}`
  - 댓글 또는 대댓글 삭제
  - 작성자 ID 검증

### PostLike API

- `POST /api/posts/{postId}/likes`
  - 게시글 좋아요 토글
  - 좋아요가 없으면 생성
  - 이미 있으면 삭제

- `GET /api/posts/{postId}/likes`
  - 게시글 좋아요 개수 조회

### CommentLike API

- `POST /api/comments/{commentId}/likes`
  - 댓글 좋아요 토글
  - 좋아요가 없으면 생성
  - 이미 있으면 삭제

- `GET /api/comments/{commentId}/likes`
  - 댓글 좋아요 개수 조회

## 10단계: elapsedTime 구현

게시글과 댓글 응답에는 `elapsedTime`을 포함하도록 했습니다.

`createdAt`을 기준으로 다음과 같은 문자열을 반환합니다.

- `방금 전`
- `3분 전`
- `2시간 전`
- `5일 전`

해당 로직은 공통 유틸 클래스인 `ElapsedTimeFormatter`로 분리했습니다.

## 11단계: DTO 스타일 변경

API 구현 다음 단계에서는 DTO 스타일을 프로젝트 스타일에 맞춰 다음 기준으로 정리했습니다.

- `record` 사용하지 않음
- 클래스 이름은 `Dto` 접미사 사용
- `@Getter` 사용
- `@AllArgsConstructor`
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
- `@Builder`
- Entity를 직접 반환하지 않고 Response DTO 사용

예시 스타일은 다음과 같습니다.

```java
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostLikeResponseDto {

    private Long postId;
    private boolean liked;
    private long likeCount;

    public static PostLikeResponseDto toDto(Long postId, boolean liked, long likeCount) {
        return PostLikeResponseDto.builder()
                .postId(postId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}
```

## 12단계: DTO와 Entity 변환 책임 정리

DTO와 Entity의 변환 책임은 다음 기준으로 정리했습니다.

- `toDto()`는 오직 DTO에만 둔다.
- `toEntity()`는 오직 Entity에만 둔다.
- DTO 객체가 Entity를 직접 만들지 않는다.
- Entity가 Request DTO를 받아 자기 자신으로 변환한다.

사용 방식은 다음과 같습니다.

```java
Post.toEntity(requestDto, author)
Comment.toEntity(requestDto, post, author, parent)
PostImage.toEntity(imageRequestDto, post)
PostLike.toEntity(requestDto, user, post)
CommentLike.toEntity(requestDto, user, comment)
```

Response 변환은 DTO가 담당합니다.

```java
PostResponseDto.toDto(post, images, likeCount, commentCount)
CommentResponseDto.toDto(comment, likeCount, replyCount)
```

## 13단계: Entity Builder 스타일 변경

Entity 생성 방식은 다음 스타일로 통일했습니다.

- 클래스 레벨 `@Builder`
- 클래스 레벨 `@AllArgsConstructor`
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)`

연관 컬렉션이 있는 엔티티는 class-level builder에서 기본값이 사라지지 않도록 `@Builder.Default`를 사용했습니다.

예를 들어 `Post`의 이미지, 댓글, 좋아요 컬렉션은 다음처럼 유지합니다.

```java
@Builder.Default
@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
private List<PostImage> images = new ArrayList<>();
```

## 14단계: Service 스타일 정리

Service 안에는 `toResponse`, `toCommentResponse`, `toReplyResponse` 같은 private mapper 메서드를 두지 않도록 했습니다.

Service는 다음 역할을 중심으로 구성했습니다.

- Entity 조회
- 작성자 검증
- 대댓글 1단계 제한 검증
- 좋아요 토글
- count 조회
- Repository 저장/삭제
- DTO의 `toDto()` 또는 Entity의 `toEntity()` 호출

변환 로직은 Service 내부 private 메서드로 두지 않고, DTO와 Entity의 정적 팩토리 메서드로 분리했습니다.

## 15단계: 최종적으로 신경 쓴 기준

개발 중 유지한 기준은 다음과 같습니다.

- 요구사항에 없는 기능은 추가하지 않기
- 인증, 인가, JWT, Security 구현하지 않기
- 실제 S3 연동, Presigned URL 발급, S3 삭제 로직 구현하지 않기
- Entity를 API 응답으로 직접 반환하지 않기
- DTO 사용하기
- `record` 대신 일반 DTO 클래스 사용하기
- DTO 이름에 `Dto` 붙이기
- DTO와 Entity 모두 `@AllArgsConstructor`, `@NoArgsConstructor`, `@Builder` 스타일로 맞추기
- `toDto()`는 DTO에만 두기
- `toEntity()`는 Entity에만 두기
- Service에 mapper 메서드 만들지 않기
- Entity에는 무분별한 Setter를 열지 않기
- `content` 수정처럼 필요한 변경 메서드만 열기
- 기본 연관관계는 `LAZY` 사용하기
- `@ManyToMany` 직접 사용하지 않기
- 좋아요는 Boolean 필드가 아니라 행 존재 여부로 표현하기
- 댓글 수와 좋아요 수는 Post 필드에 저장하지 않고 count 쿼리로 계산하기
- DB 제약조건으로 중복 좋아요와 이미지 순서 중복 막기
- 불필요한 `@Table`은 제거하고 필요한 경우만 유지하기
- 도메인별 폴더 구조 유지하기

## 16단계: 현재 컴파일 상태

최종 변경 후 다음 명령으로 컴파일을 확인했습니다.

```bash
./gradlew compileJava
```

결과는 성공입니다.

```text
BUILD SUCCESSFUL
```
