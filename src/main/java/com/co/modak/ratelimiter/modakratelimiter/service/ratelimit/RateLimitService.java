package com.co.modak.ratelimiter.modakratelimiter.service.ratelimit;

import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.rateLimitRule.dto.RateLimitRuleDTO;

public interface RateLimitService {

    void saveRateLimitRule(RateLimitRuleDTO rateLimitRule);
    void checkRateLimit(NotificationDTO notificationDTO);
}
