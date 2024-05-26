import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.co.modak.ratelimiter.modakratelimiter.controller.notification.NotificationController;
import com.co.modak.ratelimiter.modakratelimiter.service.notification.NotificationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(notificationController).build();
    }

    @Test
    public void testSendNotification() throws Exception {
        mockMvc.perform(post("/api/v1/notification/send")
                        .contentType("application/json")
                        .content("{\"message\":\"Hello\"}")) // Assuming JSON body
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent"));
    }
}
