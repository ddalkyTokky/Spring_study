package com.uzurotech.study.repository;

import com.uzurotech.study.domain.Member;
import com.uzurotech.study.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //Member 의 save랑 쫌 다름.
    public void save(Item item){
        //신규 등록
        if(item.getId() == null){
            em.persist(item);
        }
        //기존에 있는거
        else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
