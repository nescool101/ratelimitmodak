package com.co.modak.ratelimiter.modakratelimiter.controller.notification;

import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageBadRequestDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageInternalErrorDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageResourceNotFoundDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.notification.dto.NotificationDTO;
import com.co.modak.ratelimiter.modakratelimiter.service.notification.NotificationService;
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
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final static String NOTIFICATIONSENT = "Notification sent";

    private final NotificationService notificationService;

    @Operation(summary = "send notification", description = "Send notification to the user",
            tags = {"notification-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notification send successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageBadRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Rate limit rule not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResourceNotFoundDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageInternalErrorDTO.class))})
    })
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDTO notification) {
        notificationService.send(notification);
        return ResponseEntity.ok(NOTIFICATIONSENT);
    }
}