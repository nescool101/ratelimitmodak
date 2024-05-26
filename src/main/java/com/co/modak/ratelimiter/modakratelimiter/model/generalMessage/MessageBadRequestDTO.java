package com.co.modak.ratelimiter.modakratelimiter.model.generalMessage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "message bad request")
public class MessageBadRequestDTO {

    @Schema(description = "The status code of the message", example = "400")
    private int code;

    @Schema(description = "The detailed message", example = "Bad Request Op")
    private String message;
}
