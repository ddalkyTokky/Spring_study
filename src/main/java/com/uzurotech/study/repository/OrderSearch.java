package com.uzurotech.study.repository;

import com.uzurotech.study.domain.Member;
import com.uzurotech.study.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private OrderStatus orderStatus;
    private String memberUsername;
}
