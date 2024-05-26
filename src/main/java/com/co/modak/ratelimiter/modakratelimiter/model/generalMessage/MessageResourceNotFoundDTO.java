package com.co.modak.ratelimiter.modakratelimiter.model.generalMessage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Resource not found")
public class MessageResourceNotFoundDTO {


    @Schema(description = "The status code of the message", example = "404")
    private int code;

    @Schema(description = "The detailed message", example = "Resource not found")
    private String message;

}
