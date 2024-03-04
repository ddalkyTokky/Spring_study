package com.uzurotech.study.controller;

import com.uzurotech.study.domain.Address;
import com.uzurotech.study.domain.Member;
import com.uzurotech.study.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model){
        // Model 은 addAttribute 로 Att 들을 추가해서, View 로 넘어갈때 해당 Att들을 같이 넘긴다.
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member();
        log.info("this is username: " + memberForm.getUsername());
        member.setUsername(memberForm.getUsername());

        member.setAddress(new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode()));

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
