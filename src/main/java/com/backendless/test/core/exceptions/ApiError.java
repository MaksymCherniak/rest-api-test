package com.backendless.test.core.exceptions;

import lombok.Data;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
public class ApiError {
    private String code;
    private String message;
}
