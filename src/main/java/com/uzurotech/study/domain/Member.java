package com.uzurotech.study.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // 중복 방지
    @Column(unique = true)
    private String username;

    @Embedded
    private Address address;

    // FK 인데, 이것은 상황마다 다르게 걸면 된다.
//    컬렉션은 필드에서 바로 초기화 하는 것이 안전하다.
//    null 문제에서 안전하다.
//    하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다. 만
//    약 getOrders() 처럼 임의의 메서드에서 컬력션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문제가 발생
//    할 수 있다. 따라서 필드레벨에서 생성하는 것이 가장 안전하고, 코드도 간결하다.

    // 컬렉션을(테이블) 을 첫 생성시 만들고, 다시는 건들지 말것!!! (WHY??????????)
    // ~ To ~, mappedBy 는 Slave 조회의 대상이 되는 것.
    @OneToMany(mappedBy = "member") // 나는 order.member의 거울일뿐.. (slave)
    private List<Order> orders = new ArrayList<>();
}
