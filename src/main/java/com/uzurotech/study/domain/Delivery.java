package com.uzurotech.study.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue // 기본키 생성 전략
    @Column(name = "delivery_id")
    private Long id;

    // LAZY가 트랜잭션 밖에 작동되지 않는다는 단점이 있음.
    // 하지만 이것도 모두 다 방법이 있으니 가능하면, LAZY를 유지할것!!!
    // 지연로딩 중요!!!
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // EnumType.ORDINAL 은 중간에 상태값이 바뀌면 위험할 수 있다.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
