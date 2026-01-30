package com.members.controller;

import com.members.dto.MeberGetResponse;
import com.members.dto.MemberCreateRequest;
import com.members.dto.MemberCreateResponse;
import com.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<MemberCreateResponse> saveMember(@RequestBody MemberCreateRequest request){

        log.info("[API-LOG] 멤버 저장 요청: {}", request);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(request));

    }

    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<MeberGetResponse> getMember(@PathVariable("memberId") Long memberId){

        log.info("[API-LOG] 멤버 조회 요청: {}", memberId);

        return ResponseEntity.status(HttpStatus.OK).body(memberService.getOne(memberId));
    }
}
