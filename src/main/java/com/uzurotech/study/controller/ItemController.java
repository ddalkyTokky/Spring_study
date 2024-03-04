package com.uzurotech.study.controller;

import com.uzurotech.study.domain.item.Book;
import com.uzurotech.study.domain.item.Item;
import com.uzurotech.study.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm){

//        Setter 보다는 createItem 이라는 하나의 생성패턴을 정하고 사용하는 것이 훨씬 낫다.
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    //악의적 사용자가 itemId 를 임의로 조작해서 요청을 보낼 수도있다.
    // 권한 감시, 요청 등을 통해 부정 요청을 막아야만 한다!!!!!!!!!!!
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        //직접 타입 캐스팅을 하는 것이 아니라, book, movie, album 각 아이템 종류에 맞게 타입을 캐스팅 하는 것이 필요하다.
        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setName(item.getName());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setIsbn(item.getIsbn());

        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }

    //악의적 사용자가 itemId 를 임의로 조작해서 요청을 보낼 수도있다.
    // 권한 감시, 요청 등을 통해 부정 요청을 막아야만 한다!!!!!!!!!!!
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm bookForm) {

//        Book book = new Book();
//        book.setId(bookForm.getId());
//        book.setName(bookForm.getName());
//        book.setPrice(bookForm.getPrice());
//        book.setStockQuantity(bookForm.getStockQuantity());
//        book.setAuthor(bookForm.getAuthor());
//        book.setIsbn(bookForm.getIsbn());
//        itemService.saveItem(book);

        itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());

        return "redirect:/items";
    }
}
