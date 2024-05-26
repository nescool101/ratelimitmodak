package com.co.modak.ratelimiter.modakratelimiter.model.generalMessage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "message internal server error")
public class MessageInternalErrorDTO {

    @Schema(description = "The status code of the message", example = "500")
    private int code;

    @Schema(description = "The detailed message", example = "Internal Server Error")
    private String message;

}
