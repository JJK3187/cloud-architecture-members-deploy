package com.members.service;

import com.members.dto.MeberGetResponse;
import com.members.dto.MemberCreateRequest;
import com.members.dto.MemberCreateResponse;
import com.members.entity.Member;
import com.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberCreateResponse saveMember(MemberCreateRequest request) {
        Member member = new Member(request.getName(), request.getAge(), request.getMbti());

        Member savedMember = memberRepository.save(member);
        return new MemberCreateResponse(
                savedMember.getId(),
                savedMember.getName(),
                savedMember.getAge(),
                savedMember.getMbti()
        );
    }

    @Transactional(readOnly = true)
    public MeberGetResponse getOne(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 멤버 입니다.")
        );
        return new MeberGetResponse(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getMbti()
        );
    }
}
