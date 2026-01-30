package com.members.dto;

import lombok.Getter;

@Getter
public class MemberCreateResponse {

    private final Long id;
    private final String name;
    private final int age;
    private final String mbti;

    public MemberCreateResponse(Long id, String name, int age, String mbti) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }
}
