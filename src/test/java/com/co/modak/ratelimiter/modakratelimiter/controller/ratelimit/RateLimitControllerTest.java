package com.co.modak.ratelimiter.modakratelimiter.controller.ratelimit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.co.modak.ratelimiter.modakratelimiter.model.rateLimitRule.dto.RateLimitRuleDTO;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.RateLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RateLimitControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private RateLimitService rateLimitService;

    @InjectMocks
    private RateLimitController rateLimitController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(rateLimitController).build();
    }

    @Test
    public void testSaveRateLimitRule() throws Exception {
        RateLimitRuleDTO rateLimitRuleDTO = new RateLimitRuleDTO("test",5,60); // Assuming RateLimitRuleDTO has an empty constructor

        mockMvc.perform(post("/api/v1/ratelimit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateLimitRuleDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Rate limit rule saved"));
    }
}
