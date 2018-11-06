package com.backendless.test.core.model;

import lombok.Data;

import javax.ws.rs.Path;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@Path("/example")
public class EntityExample extends BaseEntity {
    private String entityName;
    private Integer someCount;
    private String description;
}
