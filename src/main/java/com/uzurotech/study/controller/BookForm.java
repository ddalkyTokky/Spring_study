package com.uzurotech.study.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;

    // Item 공통 속성
    private String name;
    private int price;
    private int stockQuantity;

    // Book 만의 속성
    private String author;
    private String isbn;
    
}
