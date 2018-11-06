package com.backendless.test.core.model;

import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Path("/example")
public class EntityExampleList extends BaseEntityList<EntityExample> {
    public List<EntityExample> getItems() {
        return items;
    }

    public void setItems(List<EntityExample> items) {
        this.items = items;
    }
}
