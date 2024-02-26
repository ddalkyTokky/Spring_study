package com.uzurotech.study.domain.service;

import com.uzurotech.study.domain.Member;
import com.uzurotech.study.domain.repository.MemberRepository;
//import org.junit.Test;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
// 스프링 부트를 띄운 채로 테스트할 수 있게 해줌.
// @Autowired가 작동하게 해중.
@SpringBootTest
//트랜첵션을 일단 걸고
//자동 롤백을 실행함.
//단, Test 에서만 롤백함. 서비스나 기타 레포 클래스에서는 롤백하지 않음!!!
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @Test
//    @Rollback(false) // 롤백을 컨트롤 할 수 있음.
    public void sign_up() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("Lee");

        //when
        Long saveId = memberService.join(member);

        //then
        // 해당 구문을 추가하면, 영속성 캐시에서 DB로 쿼리가 강제로 나간다.
        // 따라서, 실제 insert 쿼리를 볼 수 있게 된다.
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void duplicate_member_exception() throws Exception {
        Member member = new Member();
        member.setUsername("Soon");
        Member member2 = new Member();
        member2.setUsername("Soon");

        //when
        memberService.join(member);

//        try{
//            memberService.join(member2); // 예상 예외 발생 포인트
//        }
//        catch (IllegalStateException e){
//            return;
//        }

        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }); // 예상 예외 발생 포인트

        //then
//        Assert.fail("duplicate_member_exception_Test_fail");
    }
}