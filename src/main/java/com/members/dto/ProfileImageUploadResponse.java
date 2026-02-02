package com.members.dto;

import lombok.Getter;

@Getter
public class ProfileImageUploadResponse {

    private final String imageUrl;

    public ProfileImageUploadResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
