package com.uzurotech.study.service;

import com.uzurotech.study.domain.item.Book;
import com.uzurotech.study.domain.item.Item;
import com.uzurotech.study.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 해당 서비스는 거의 컨트롤러와 리포지토리를 연결만 해주는 수준이다.
// 이런 서비스에 대해서는 작성하는 것을 충분히 고려할 필요가 있다.

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //얘가 readOnly 보다 더 가까이에 있기 때문에, 우선권을 가짐.
    @Transactional
    public void  saveItem(Item item){
        itemRepository.save(item);
    }

    //마찬가지로 타입 캐스팅을 바로 하는게 아니고, 아이템 종류에 맞는 타입으로 해주어야 맞다.
    //만약 파라미터가 너무 많아지면, 별도의 DTO 객체를 만들어서 전달받아도 된다.
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        //Setter 남발 금지!!!!!!!!
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
//        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
