package com.co.modak.ratelimiter.modakratelimiter.service.notification.impl;

import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.co.modak.ratelimiter.modakratelimiter.service.notification.NotificationService;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.RateLimitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RateLimitService rateLimitService;
    private final static String STATUS = "Status";
    private final static String NEWS = "News";
    private final static String MARKETING = "Marketing";

    @Override
    public void send(NotificationDTO notificationDTO){

        rateLimitService.checkRateLimit(notificationDTO);

    }

}
