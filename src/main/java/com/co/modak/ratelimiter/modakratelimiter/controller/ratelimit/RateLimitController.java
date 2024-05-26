package com.co.modak.ratelimiter.modakratelimiter.controller.ratelimit;

import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageBadRequestDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageInternalErrorDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageResourceNotFoundDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.rateLimitRule.dto.RateLimitRuleDTO;
import com.co.modak.ratelimiter.modakratelimiter.service.ratelimit.RateLimitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ratelimit")
@AllArgsConstructor
public class RateLimitController {

    private final static String RATELIMITRULESAVED = "Rate limit rule saved";

    private final RateLimitService rateLimitService;

    @Operation(summary = "create rate limit rule", description = "payload the rate limit rule to be saved",
            tags = {"rateLimit-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rate limit successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageBadRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageInternalErrorDTO.class))})
    })

    @PostMapping
    public ResponseEntity<String> saveRateLimitRule(@RequestBody RateLimitRuleDTO rateLimitRuleDTO) {
        rateLimitService.saveRateLimitRule(rateLimitRuleDTO);
        return ResponseEntity.ok(RATELIMITRULESAVED);
    }

}
