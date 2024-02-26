package com.uzurotech.study.repository;

import com.uzurotech.study.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext 를 @Autowired or @RequiredArgsConstructor 로 바꿀 수 있음.
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // JP-QL 테이블이 아닌 엔티티를 상대로 쿼리한다고 생각하면 편함.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String input_name) {
        return em.createQuery("select m from Member m where m.username = :sql_name", Member.class)
                .setParameter("sql_name", input_name)
                .getResultList();
    }
}
