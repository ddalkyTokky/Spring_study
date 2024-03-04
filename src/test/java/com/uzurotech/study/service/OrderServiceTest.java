package com.uzurotech.study.service;

import com.uzurotech.study.domain.*;
import com.uzurotech.study.domain.item.Book;
import com.uzurotech.study.domain.item.Item;
import com.uzurotech.study.exception.NotEnoughStockException;
import com.uzurotech.study.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// 좋은 테스트 방식은 아니다. 순수하게 DB 디펜던시 및 스프링 디펜던시 없는 메소드 하나에 대한 단위 테스트로 진행되는 것이 제일 좋다.
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

//    @Autowired
//    ItemService itemService;
//    @Autowired
//    MemberService memberService;

//    단위 테스트를 위해서는 Order를 제외한 다른 요소들은 EntityManager, 또는 Repository를 직접 불러와서 persist하는 것도 방법이다.
    @Autowired
    EntityManager em;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void item_order() throws Exception {
        //given
        Member member = createMember();

        int bookPrice = 3920;
        int bookStockQuantity = 100;

        Book book = createBook(bookPrice, bookStockQuantity);

        int orderCount = 10;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "Order Status must be ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "Order type count must be correct");
        assertEquals(bookPrice * orderCount, getOrder.getTotalPrice(), "Total Order Price must be correct");
        assertEquals((bookStockQuantity - orderCount), book.getStockQuantity(), "StockQuntity -= orderCount");
    }

    @Test
    public void cancel_order() throws Exception {
        //given (주어진 상황)
        int bookPrice = 3920;
        int bookStockQuantity = 100;
        int orderCount = 5;

        Book book = createBook(bookPrice, bookStockQuantity);
        Member member = createMember();

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when (무슨일이 있었을 때)
        orderService.cancelOrder(orderId);

        //then (이런 일이 있어야 한다.)
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "Order Status must be CANCEL");
        assertEquals(bookStockQuantity, book.getStockQuantity(), "StockQuntity -= orderCount");
    }

    //removeStock() 하나에 대하여서만도 단위 테스트가 필요하다.
    @Test
    public void quantity_over_order_deny() throws Exception {
        //given
        Member member = createMember();

        int bookPrice = 3920;
        int bookStockQuantity = 100;

        //Book말고 Item을 쓰는 이유?
        Book book = createBook(bookPrice, bookStockQuantity);

        int orderCount = 101;

        //when
        Assertions.assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });

        //then
    }

    //==단위 테스트 준비를 위한 객체 생성용 함수==//

    private Book createBook(int bookPrice, int bookStockQuantity) {
        Book book = new Book();
        book.setName("HelloWorldJAVA");
        book.setPrice(bookPrice);
        book.setStockQuantity(bookStockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("Lee");
        member.setAddress(new Address("GM", "Sea", "39-128"));
        em.persist(member);
        return member;
    }
}

