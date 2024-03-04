package com.uzurotech.study.controller;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberForm {

    @NotBlank(message = "Member Username is necessary!!")
    private String username;

    //Address class를 직접 갖다 써도 무방.
    private String city;
    private String street;
    private String zipcode;
}
