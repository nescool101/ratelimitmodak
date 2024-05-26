package com.co.modak.ratelimiter.modakratelimiter.model.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Notification to be sent by user")
public record NotificationDTO(String type, String userMail, String message) {
}