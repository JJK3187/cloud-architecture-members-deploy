package com.members.controller;

import com.members.dto.*;
import com.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @PostMapping(value = "/api/members/{memberId}/profile-image)", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileImageUploadResponse> uploadProfileImage(
            @PathVariable("memberId") Long memberId,
            @RequestPart("file") MultipartFile file ) throws IOException
    {
        String imageUrl = memberService.uploadProfileImage(memberId, file);
        return ResponseEntity.ok(new ProfileImageUploadResponse(imageUrl));
    }

    @GetMapping("/api/members/{memberId}/profile-image)")
    public ResponseEntity<ProfileImageGetResponse> getProfileImage(@PathVariable("memberId") Long memberId) {
        String presignedUrl = memberService.getPresignedUrl(memberId);

        String expiration = LocalDateTime.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return ResponseEntity.ok(new ProfileImageGetResponse(presignedUrl, expiration));
    }
}
