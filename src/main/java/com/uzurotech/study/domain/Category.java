package com.uzurotech.study.domain;

import com.uzurotech.study.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 시물에서 쓰기 굉장히 어려운 구조임.
    // 가능하면 다대다 구조 + 맵핑 테이블이 필요한 이런 구조는 쓰지 말것!!
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    //셀프 양방향 맵핑
    // ~~ ToOne은 EAGER가 디폴트이다.
    // 무조건 LAZY로 바꿔주자!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    //셀프 양방향 맵핑
    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    public void addChildCategory(Category child){
        this.children.add(child);
        child.setParent(this);
    }
}
