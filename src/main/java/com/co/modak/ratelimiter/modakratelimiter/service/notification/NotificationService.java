package com.co.modak.ratelimiter.modakratelimiter.service.notification;

import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;

public interface NotificationService {

    void send(NotificationDTO notification);
}
