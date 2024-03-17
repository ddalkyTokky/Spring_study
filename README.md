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
%EC%97%90%EB%9F%AC-%EB%B0%9C%EC%83%9D https://stackoverflow.com/questions/42677526/gradle-compilejava-error-package-orgjunit-does-not-exist)
>
Test 빌드시 Junit 4, 5 때문에 발생하는 문제
아래 방법으로 해결했으며, 위 참고 링크에 다양한 시도들이 기록되어 있다.

```
// import org.junit.Test; // 를 아래 구문으로 대체
import org.junit.jupiter.api.Test;
```
