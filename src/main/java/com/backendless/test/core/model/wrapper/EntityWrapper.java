package com.backendless.test.core.model.wrapper;

import com.backendless.test.core.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"modified", "entity"})
public class EntityWrapper {
    private BaseEntity entity;
    private String ref;
    private Long modified;
}
