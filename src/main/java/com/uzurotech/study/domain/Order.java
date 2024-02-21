package com.uzurotech.study.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "oerder_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // FK 인데, 이것은 상황마다 다르게 걸면 된다.
    // JoinColumn , name, 은 Master, 주인, 조회의 주체가 되는 것.
    @JoinColumn(name = "member_id")
    private Member member;

    // persist 로 원래 디비에 push 한다.
    // 그런데, 를 쓰면 Order 한번의 push 로 하위 영역, orderitems, delivery와 같은 것들도 같이 persist 된다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //===연관관게 메서드===//
    //위치는 핵심 컨트롤러가 하는게 좋음.//
    // 양쪽 다 있으면 당연히 유지보수가 어려워 지겠죵?//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderitem){
        this.orderItems.add(orderitem);
        orderitem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
