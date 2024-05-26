package com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.impl;

import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceBadRequestException;
import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceNotFoundException;
import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.rateLimitRule.dto.RateLimitRuleDTO;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.RateLimitService;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.SendMailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class SendMailServiceImpl implements SendMailService {

    private final static String JSONPARSEEXCEPTION = "JsonParseException";
    private final static String RATELIMITRULENOTFOUND = "RateLimitRule not found";

    private StringRedisTemplate jedis;
    private ObjectMapper objectMapper;

    @Override
    public int sendMail(NotificationDTO notificationDTO){

        try {

            Email from = new Email("nescool101@gmail.com");
            String subject = "Sending mail with SendGrid and rateLimit";
            Email to = new Email("Jorgetovar621@gmail.com");
            Content content = new Content("text/plain", "sending test mail by nescao with Java");
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid("NOTREALAPIKEY");
            Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
                return  response.getStatusCode();
        } catch (Exception exception) {
            throw new ResourceBadRequestException(JSONPARSEEXCEPTION);
        }
    }

}
