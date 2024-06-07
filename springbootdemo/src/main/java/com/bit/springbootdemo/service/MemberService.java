package com.bit.springbootdemo.service;

import com.bit.springbootdemo.dto.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Controller, @RestController, @Service : 객체를 생성해주는 어노테이션
@Service
public interface MemberService {
    void join(MemberDto memberDto);

    int memberCnt(MemberDto memberDto);

    Optional<MemberDto> login(MemberDto memberDto);

    List<MemberDto> memberList();
}
