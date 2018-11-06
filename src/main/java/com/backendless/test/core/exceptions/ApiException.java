package com.backendless.test.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class ApiException extends RuntimeException {
    private String message;

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
