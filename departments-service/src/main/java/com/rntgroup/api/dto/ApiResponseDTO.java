package com.rntgroup.api.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ApiResponseDTO<T> {

    private boolean isSuccess;
    private String message;
    private T response;

}
