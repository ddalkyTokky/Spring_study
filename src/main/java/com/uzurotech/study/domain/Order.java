package com.uzurotech.study.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
//생성자를 protected로 선언하는 것과 같은 효과를 발휘함.
//생성 패턴을 한가지로 강제하는 효과가 있음.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 또한, 이러한 CasCade는 매우 조심하여야 한다. 만약, Order가 아닌 다른 엔티티들도 OrderItem, Delivery등을 사용하는 또 다른 Owner가 된다면, 지양해야 한다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //===연관관게 메서드===//
    //위치는 핵심 컨트롤러가 하는게 좋음.//
    //양쪽 다 있으면 당연히 유지보수가 어려워 지겠죵?//
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

    //==생성 메서드--//
    // 상황에 따라서는 더 복잡해질 수도 있음.
    // 주의 필요.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem: orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비지니스 로직==//
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("Already Completed Delivery. Cannot cancel");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: this.getOrderItems()){
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    public int getTotalPrice(){
//        int totalPrice = 0;
//        for (OrderItem orderItem: this.orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
        return this.orderItems
                .stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
