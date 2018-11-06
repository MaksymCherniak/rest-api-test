package com.backendless.test.core.service;

import com.backendless.test.core.model.EntityExample;

import java.util.List;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
public interface ExampleService {
    EntityExample createEntity(EntityExample example);

    EntityExample getExampleById(String id);

    List<EntityExample> getAllExamples();

    EntityExample updateExample(EntityExample example);

    void deleteExample(EntityExample example);
}
