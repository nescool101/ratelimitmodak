package com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.impl;

import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceBadRequestException;
import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class SendMailServiceImplTest {

    @Mock
    private SendGrid sendGrid;

    @InjectMocks
    private SendMailServiceImpl sendMailService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMail() throws Exception {
        NotificationDTO notificationDTO = new NotificationDTO("user@example.com","News","test message");

        Response mockResponse = new Response();
        mockResponse.setStatusCode(200);

        when(sendGrid.api(any())).thenReturn(mockResponse);

        int statusCode = sendMailService.sendMail(notificationDTO);

        assertEquals(200, statusCode);
    }

    @Test
    public void testSendMailFailed() throws Exception {
        NotificationDTO notificationDTO = new NotificationDTO("user@example.com","News","test message");

        when(sendGrid.api(any())).thenThrow(new IOException("Send failed"));

        try {
            sendMailService.sendMail(notificationDTO);
            fail("Expected an IOException to be thrown");
        } catch (ResourceBadRequestException e) {
            assertEquals("JsonParseException", e.getMessage());
        }
    }

}
