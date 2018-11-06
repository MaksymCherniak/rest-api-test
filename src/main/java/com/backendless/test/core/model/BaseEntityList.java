package com.backendless.test.core.model;

import java.util.List;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
public abstract class BaseEntityList<T extends BaseEntity> {
    protected List<T> items;

    public abstract List<T> getItems();

    public abstract void setItems(List<T> items);
}
