# _**실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발**_
>[김영한의 스프링 부트와 JPA 실무 완전 정복 로드맵](https://www.inflearn.com/roadmaps/149)

# _**수강하면서 생겼던 문제점들**_
## _**package org.junit.runner does not exist**_
>[참고: gradle compileJava error: package org.junit does not exist](https://stackoverflow.com/questions/42677526/gradle-compilejava-error-package-org-junit-does-not-exist)

Gradle 빌드 시 발생하는 오류임.
IntelliJ 빌드 시에는 발생하지 않음.
그러나, Gradle 빌드시에도 발생하지 않게 하려면, 아래 내용을 build.gradle에 추가하면 됨.

>```dependencies {      
> testImplementation 'junit:junit:4.12'      
> implementation 'junit:junit:4.12'      
>}  ```    
