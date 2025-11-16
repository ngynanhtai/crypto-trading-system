package com.aquariux.crypto.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
    private Object data;
    private String message;
    private HttpStatus status = HttpStatus.OK;
}
