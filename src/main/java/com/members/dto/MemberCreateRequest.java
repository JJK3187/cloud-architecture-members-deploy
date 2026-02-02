package com.members.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    private String name;
    private int age;
    private String mbti;

}
