package com.bit.springbootdemo.controller;

import com.bit.springbootdemo.dto.MemberDto;
import com.bit.springbootdemo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

//@Controller, @RestController : 이 클래스를 http 요청을 받아서 처리할 수 있는 클래스로 만들어 준다.
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // ioc 컨테이너가 객체생성과 넣어줌을 자동으로 spring에서 해줌 (Inverse Of Control)

    @PostMapping("/login")
    public ModelAndView login(MemberDto memberDto){
        ModelAndView mav = new ModelAndView();
        int memberCnt = memberService.memberCnt(memberDto);

        if(memberCnt == 0){
            mav.setViewName("index");
            mav.addObject("errorMsg", "id not exist");
            return mav;
        }

        Optional<MemberDto> loginMember = memberService.login(memberDto);

        if(loginMember.isEmpty()){
            mav.setViewName("index");
            mav.addObject("errorMsg", "wrong password");
            return mav;
        }


        mav.setViewName("list");
        mav.addObject("loginMember", loginMember.get());

        List<MemberDto> memberDtoList = memberService.memberList();
        mav.addObject("memberList", memberDtoList);
        return mav;
    }

    // 데이터 필요없이 화면으로만 이동해주는 기능
    @GetMapping("/join")
    public ModelAndView join(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("join");
        return mav;
    }


    // @ModelAttribute 로 지정된 객체는 생성자를 자동으로 호출하여 객체를 하나 생성한다.
    // 객체의 인스턴스 변수와 동일한 키 값 으로 넘어오는 데이터에 대해 자동으로 Setter메소드를 동작시켜준다.
    // <input name="username"> 키값으로 username. 동일한 네임이 있으면 setter메소드로 변수에 담아준다.
    // setUsername(넘어온데이터) 자동실행
    // @ModelAttribute 는 생략 가능하다
    @PostMapping("/join")
    public ModelAndView join(/*@ModelAttribute*/ MemberDto memberDto){
        ModelAndView mav = new ModelAndView();

        System.out.println(memberDto.getUsername());
        System.out.println(memberDto.getPassword());

        // MVC 패턴
        // Model Veiw Controller
        // Model => DB와 연동되는 Service, DAO나 Mapper 객체
        // View => 화면(html, jsp)
        // Controller => 화면에서 데이터를 받거나 DB에서 뽑아온 데이터를 화면으로 넘겨주는 클래스(어노테이션)
        memberService.join(memberDto);

        mav.setViewName("index");
        return mav;
    }


}
