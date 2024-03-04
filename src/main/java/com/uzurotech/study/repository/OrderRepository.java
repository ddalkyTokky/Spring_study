package com.uzurotech.study.repository;

import com.uzurotech.study.domain.Member;
import com.uzurotech.study.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
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

    public List<Order> findAllByString(OrderSearch orderSearch){
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        log.info(orderSearch.getMemberUsername());

        // 주문 상태 검색
        if(orderSearch.getOrderStatus() != null){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }
            else{
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        // 회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberUsername())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }
            else{
                jpql += " and";
            }
            jpql += " m.username like concat('%', :username, '%')";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000개까지 표시

        if(orderSearch.getOrderStatus() != null){
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberUsername())){
            query = query.setParameter("username", orderSearch.getMemberUsername());
        }

        return query.getResultList();
    }
}
