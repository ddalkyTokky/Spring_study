package com.uzurotech.study.domain.item;

import com.uzurotech.study.domain.Category;
import com.uzurotech.study.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==//
    //바깥에서 계산해서 Set하지 말고!!
    //반드시 해당 요소를 갖고있는 class또는 entity가 직접 계산하게 할것!!!
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        if(this.stockQuantity < quantity){
            throw new NotEnoughStockException("it is more than stockQuantity");
        }
        this.stockQuantity -= quantity;
    }
}
