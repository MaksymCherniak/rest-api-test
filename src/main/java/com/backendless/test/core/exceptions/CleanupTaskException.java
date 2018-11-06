package com.backendless.test.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@AllArgsConstructor
public class CleanupTaskException extends RuntimeException {
    private String message;

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
