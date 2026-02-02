package com.members.dto;

import lombok.Getter;

@Getter
public class ProfileImageGetResponse {

    private final String presignedUrl;
    private final String expiration;

    public ProfileImageGetResponse(String presignedUrl, String expiration) {
        this.presignedUrl = presignedUrl;
        this.expiration = expiration;
    }
}
