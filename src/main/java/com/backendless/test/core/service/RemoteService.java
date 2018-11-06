package com.backendless.test.core.service;

import com.backendless.test.core.model.BaseEntity;
import com.backendless.test.core.model.BaseEntityList;
import com.sun.jersey.api.client.ClientResponse;

import java.util.Map;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
public interface RemoteService {
    //Get
    <T extends BaseEntity> T get(Class<T> entityType, String refURL);

    <T extends BaseEntity> T getById(Class<T> entityType, String id);

    <T extends BaseEntityList> T getList(Class<T> entityType, Map<String, String> searchCriteria);

    <T extends BaseEntityList> T getList(Class<T> entityType, Map<String, String> searchCriteria, String path);

    //Create
    <T extends BaseEntity> ClientResponse create(T entity);

    <T extends BaseEntity> ClientResponse create(T entity, String path);

    <T extends BaseEntity> T createAndGet(T entity);

    <T extends BaseEntity> T createAndGet(T entity, String path);

    //Update
    <T extends BaseEntity> T updateAndGet(T entity);

    //Delete
    void delete(String refURL);

    void deleteById(Class<? extends BaseEntity> clazz, String id);
}
