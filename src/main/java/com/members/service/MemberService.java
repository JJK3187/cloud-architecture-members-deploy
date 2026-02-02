package com.members.service;

import com.members.dto.MeberGetResponse;
import com.members.dto.MemberCreateRequest;
import com.members.dto.MemberCreateResponse;
import com.members.entity.Member;
import com.members.repository.MemberRepository;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3Template s3Template;
    private final S3Presigner s3Presigner;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

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

    @Transactional
    public String uploadProfileImage(Long memberId, MultipartFile file) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 멤버 입니다.")
        );
        String fileName = "profiles/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        S3Resource s3Resource = s3Template.upload(bucket, fileName, file.getInputStream(),
                ObjectMetadata.builder().contentType(file.getContentType()).build());

        String imageUrl = s3Resource.getURL().toString();
        member.updateProfileImageUrl(imageUrl, fileName);
        return imageUrl;
    }

    public String getPresignedUrl(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 멤버 입니다.")
        );
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(member.getS3Key())
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(7))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);

        return presignedRequest.url().toString();
    }
}
