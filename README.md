# _**실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발**_
>[김영한의 스프링 부트와 JPA 실무 완전 정복 로드맵](https://www.inflearn.com/roadmaps/149)

# _**수강하면서 생겼던 문제점들**_
## _**package org.junit.runner does not exist**_
>[참고: gradle compileJava error: package org.junit does not exist](https://stackoverflow.com/questions/42677526/gradle-compilejava-error-package-org-junit-does-not-exist)

Gradle 빌드 시 발생하는 오류임.
IntelliJ 빌드 시에는 발생하지 않음.
그러나, Gradle 빌드시에도 발생하지 않게 하려면, 아래 내용을 build.gradle에 추가하면 됨.

```
dependencies {      
 testImplementation 'junit:junit:4.12'      
 implementation 'junit:junit:4.12'      
}
```

## _**Gradle complieJava error: package org.junit does not exist**_
>[참고: 테스트 도중 에러 발생](https://www.inflearn.com/questions/15495/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EB%8F%84%EC%A4%91-
%EC%97%90%EB%9F%AC-%EB%B0%9C%EC%83%9D)
>[참고: gradle compileJava error: package org.junit does not exist](https://stackoverflow.com/questions/42677526/gradle-compilejava-error-package-org-junit-does-not-exist)

Test 빌드시 Junit 4, 5 때문에 발생하는 문제
아래 방법으로 해결했으며, 위 참고 링크에 다양한 시도들이 기록되어 있다.

```
// import org.junit.Test; // 를 아래 구문으로 대체
import org.junit.jupiter.api.Test;
```

## _**@Test(expected = ) 인식 안됨**_
>[참고: [Spring] JUnit5에서의 Exception 처리](https://dkswnkk.tistory.com/441)

Junit4 에서 Junit5로 넘어가면서 해당 필드가 삭제되었다. (@Test는 여전히 존재함)
Junit5 에서는 아래 구문으로 Exception을 처리해야 한다.

```
Assertions.assertThrows(IllegalStateException.class, () -> {
 memberService.join(member2); // 예외를 발생시켜야 되는 구문
});
```

## _**javax.validation import 불가**_
아래 구문을
```
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
```
아래 구문으로 변경함.
```
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
```
스프링 3.x 부터 javax => jakarta 로 annotation이 변경됨.
```
implementation 'org.springframework.boot:spring-boot-starter-validation'
```
종속성은 위 문장으로 고정.

# _**수강전 알고있으면 도움되는 팁 or 추가설명**_
## _**Spring Injection 과 @AutoWired**_
>[참고: [Spring] 의존성 주입(Dependency Injection, DI)이란? 및 Spring이 의존성 주입을 지원하는 이유](https://mangkyu.tistory.com/150)
위 링크들의 양이 방대해서 직접 읽어보는 것을 추천한다.. 링크 문제시 문의 이순용
준영속 엔티티 와 변경 감지, 병합에 대한 이해
>[참고: Section7 변경 감지와 병함 (merge)] https://www.inflearn.com/course/lecture?courseSlug=%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-1&unitId=24309&tab=curriculum)
준영속 엔티티란 영속성 컨텍스트가 더는 관리하지 않는 엔티티이다.
이를 해결하기 위한 방법에는 두가지가 있다.
1. 변경 감지.
2. 병합.
변경 감지는 직접 JPA에서 업데이트 하고자 하는 객체의 엔티티 ID로 객체를 찾아와서, JPA가 관리하는 영속성 엔티티로 만든 다음, 당 객체를 수정하는
것을 의미한다.
병합은 merge함수를 써서, 변경 감지용 코드를 대체하는 역할이라고 이해하면 편하다.
병합 (merge 함수)가 반환하는 객체는 영속성 엔티티이다. 파라미터로 전달된 엔티티는 여전히 준영속이다!!
또한 merge는 모든 필드를 강제로 변환한다. 선택할 수 없다. 따라서, 전달하지 않은 필드에 대해서는 값을 유지하는 것이 아닌, null로 초기화해버린다!!
@DiscriminatorColumn(name = "dtype")
참고: https://ict-nroo.tistory.com/128
객체와 달리 테이블들은 상속관계를 정의할 수 없다.
따라서 아래 세 젼략을 사용하여 맵핑하고, 그 부모-자식 관계를 명확히 한다.
1. 조인 전략 (JOINED_TABLE)
2. 단일 테이블 전략 (SINGLE_TABLE)
3. 모든 부모 클래스의 컬럼을 자식으로 내리기 (TABLE_PER_CLASS)
수강평 및 개인평 (순용)
참고: https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-
1#reviews

입문용 난이도로 아주 적절한 난이도.
Computer Science 관련 전공 지식이 충분하고, 적절한 경험이 있는 개발자라면 쉽게 이해할 수 있는 난이도이다.
거기에 더해, 깔끔한 설명과 https://www.inflearn.com/course/ORM-JPA-Basic 을 듣지 않아도 어느 정도 유추가 되는 자세한 설명이 가장 큰 장점이다.
다만, 본인은 https://www.inflearn.com/course/ORM-JPA-Basic 을 듣지 않아, 정확한 비교가 아닌 추측임에 유의하기 바란다
