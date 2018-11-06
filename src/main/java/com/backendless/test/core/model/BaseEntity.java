package com.backendless.test.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@EqualsAndHashCode
public class BaseEntity {
    private String id;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
