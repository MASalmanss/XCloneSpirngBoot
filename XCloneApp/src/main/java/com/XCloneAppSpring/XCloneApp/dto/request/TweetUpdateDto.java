package com.XCloneAppSpring.XCloneApp.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class TweetUpdateDto {
    private UUID id;
    private String content;
}
