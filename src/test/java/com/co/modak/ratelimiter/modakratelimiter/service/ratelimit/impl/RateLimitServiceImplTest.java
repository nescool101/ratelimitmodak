package com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.impl;

import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceBadRequestException;
import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceNotFoundException;
import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.rateLimitRule.dto.RateLimitRuleDTO;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.SendMailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;

public class RateLimitServiceImplTest {

    @Mock
    private StringRedisTemplate jedis;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private SendMailService sendMailService;
    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RateLimitServiceImpl rateLimitService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(jedis.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testSaveRateLimitRule() throws Exception {
        RateLimitRuleDTO rateLimitRuleDTO = new RateLimitRuleDTO("News",5,60);
        rateLimitService.saveRateLimitRule(rateLimitRuleDTO);
        verify(valueOperations).set("News", null);
    }

    @Test(expected = ResourceBadRequestException.class)
    public void testSaveRateLimitRuleThrowsException() throws Exception {
        RateLimitRuleDTO rateLimitRuleDTO = new RateLimitRuleDTO("News",5,60);

        when(objectMapper.writeValueAsString(rateLimitRuleDTO)).thenThrow(new RuntimeException());
        rateLimitService.saveRateLimitRule(rateLimitRuleDTO);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCheckRateLimitRuleNotFound() throws Exception {
        NotificationDTO notificationDTO = new NotificationDTO("News","test@mail.com","test mail");

        when(valueOperations.get(anyString())).thenReturn(null);
        rateLimitService.checkRateLimit(notificationDTO);
    }

    @Test
    public void testCheckRateLimitRuleSendMail() throws Exception {
        NotificationDTO notificationDTO = new NotificationDTO("News","test@mail.com","test mail");


        RateLimitRuleDTO rateLimitRuleDTO = new RateLimitRuleDTO("News",5,60);


        when(valueOperations.get(notificationDTO.type())).thenReturn("{\"limit\": 10, \"periodInSeconds\": 60}");
        when(objectMapper.readValue(anyString(), eq(RateLimitRuleDTO.class))).thenReturn(rateLimitRuleDTO);
        when(valueOperations.get("user@example.com:API")).thenReturn("5");

        rateLimitService.checkRateLimit(notificationDTO);
    }
}
