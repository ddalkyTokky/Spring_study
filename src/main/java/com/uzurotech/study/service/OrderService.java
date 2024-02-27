package com.uzurotech.study.service;

import com.uzurotech.study.domain.Delivery;
import com.uzurotech.study.domain.Member;
import com.uzurotech.study.domain.Order;
import com.uzurotech.study.domain.OrderItem;
import com.uzurotech.study.domain.item.Item;
import com.uzurotech.study.repository.ItemRepository;
import com.uzurotech.study.repository.MemberRepository;
import com.uzurotech.study.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // OrderItem의 생성자가 protected로 선언되어서 아래 구문은 사용 불가해진다.
        // OrderItem orderItem1 = new OrderItem();

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        //원래대로라면, order를 save(persist)하기 전에 OrderItem들도 save(persist) 해주어야 한다. 하지만, 우리는 Order.java #L29 #L32 에 cascade = CascadeType.ALL 조건을 걸어주었기에, 무시해도 된다. <<<중요!!!!>>>
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        //JPA를 이용하면, 엔티티 내의 바뀐 값들에 대해 자동으로 체크하고 알아서 쿼리를 생성, 전송한다. JPA의 가장 좋은점 중 하나!!!
        //따로 쿼리를 다시 짤 필요가 없다!!
        order.cancel();
    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return OrderRepository.findAll(orderSearch);
//    }

}
