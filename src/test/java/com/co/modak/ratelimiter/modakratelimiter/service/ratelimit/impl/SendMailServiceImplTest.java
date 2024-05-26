package com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.impl;

import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceBadRequestException;
import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SendMailServiceImplTest {

    @Mock
    private SendGrid sendGrid;

    @InjectMocks
    private SendMailServiceImpl sendMailService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMailFailed() throws Exception {
        NotificationDTO notificationDTO = new NotificationDTO("user@example.com","News","necao test msj");

        Response mockResponse = new Response();
        mockResponse.setStatusCode(400);
        mockResponse.setBody("Success");
        mockResponse.setHeaders(new HashMap<>());

        when(sendGrid.api(any())).thenReturn(mockResponse);

        int statusCode = sendMailService.sendMail(notificationDTO);

        assertEquals(400, statusCode);
    }

}
