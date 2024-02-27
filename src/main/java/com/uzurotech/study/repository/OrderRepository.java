package com.uzurotech.study.repository;

import com.uzurotech.study.domain.Member;
import com.uzurotech.study.domain.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch){
//        정적 쿼리임.
//        QueryDSL로 바뀌어야 될 필요가 있음.
        return em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status" +
                " and m.username like :username",
                Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("username", orderSearch.getMemberUsername())
                .setFirstResult(0) // n번째부터 1000개까지.
                .setMaxResults(1000) // 페이징 설정 가능.
                .getResultList();
    }
}
