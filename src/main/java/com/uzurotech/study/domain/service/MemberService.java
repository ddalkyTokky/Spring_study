package com.uzurotech.study.domain.service;

import com.uzurotech.study.domain.Member;
import com.uzurotech.study.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// jakarta가 아닌 Transactional 은 스프링 껄로 선택하자.
// readOnly 기본값 셋팅
@Transactional(readOnly = true)
// @AutoWired 와 달리, final만 생성자로 잡음.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    // 필드 인젝션 외부에서 생성한 "클래스"가 아닌 "객체"를 주입한다.
    // 생성자

//    @AllArgsConstructor 모든 필드에 대해서 아래 생성자들을 모두 만들어줌.
    // @Autowired 어노테이션 없이도 자동 할당됨.
//    private MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    // 얘만 (readOnly = false)
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        // 현재 코드는 두 명이 동시에 같은 이름으로 가입할 가능성이 있다.
        // 그렇기에, DB에서 UNIQUE 제약 조건을 걸어주어야 한다.
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        //EXCEPTION
        if(!memberRepository.findByName(member.getUsername()).isEmpty()){
            throw new IllegalStateException("there is existed member with name" + member.getUsername());
        }

    }

    // 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
