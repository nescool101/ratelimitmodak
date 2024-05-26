package com.co.modak.ratelimiter.modakratelimiter.model.rateLimitRule.dto;

public record RateLimitRuleDTO(String type, int limit, int periodInSeconds){
}
