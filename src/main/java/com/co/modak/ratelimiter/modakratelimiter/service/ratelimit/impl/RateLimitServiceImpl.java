package com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.impl;

import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceBadRequestException;
import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceNotFoundException;
import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.rateLimitRule.dto.RateLimitRuleDTO;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.RateLimitService;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.SendMailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RateLimitServiceImpl implements RateLimitService {

    private final static String JSONPARSEEXCEPTION = "JsonParseException";
    private final static String RATELIMITRULENOTFOUND = "RateLimitRule not found";

    private StringRedisTemplate jedis;
    private ObjectMapper objectMapper;
    private final SendMailService sendMailService;

    @Override
    public void saveRateLimitRule(RateLimitRuleDTO rateLimitRule){

        try {

            String key = rateLimitRule.type();
            String value = objectMapper.writeValueAsString(rateLimitRule);
            jedis.opsForValue().set (key, value);

        } catch (Exception exception) {
            throw new ResourceBadRequestException(JSONPARSEEXCEPTION);
        }
    }

    @Override
    public void checkRateLimit(NotificationDTO notificationDTO) {
        String rateLimitRuleJson = jedis.opsForValue().get(notificationDTO.type());
        if (rateLimitRuleJson == null) {
            throw new ResourceNotFoundException(RATELIMITRULENOTFOUND);
        }
        RateLimitRuleDTO rateLimitRule;
        try {
            rateLimitRule = objectMapper.readValue(rateLimitRuleJson, RateLimitRuleDTO.class);
        } catch (Exception exception) {
            throw new ResourceBadRequestException(JSONPARSEEXCEPTION);
        }

        String key = notificationDTO.userMail() +":"+ notificationDTO.type();
        String countJson = jedis.opsForValue().get(key);

        int count = countJson != null ? Integer.parseInt(countJson) : 0;

        if (count < rateLimitRule.limit()) {
            System.out.println("send notification: "+notificationDTO.type());
            int myMailSvcCode= sendMailService.sendMail(notificationDTO);
            System.out.println("send notification mail code: "+myMailSvcCode);
            jedis.opsForValue().set(key, String.valueOf(count + 1));
            jedis.expire(key, rateLimitRule.periodInSeconds(), TimeUnit.SECONDS);
            return;
        }
        System.out.println("send notification: "+notificationDTO.type()+" does not possible send notification. Limit exceeded.");
    }
}
