package com.backendless.test.core.service.impl;

import com.backendless.test.core.model.EntityExample;
import com.backendless.test.core.model.EntityExampleList;
import com.backendless.test.core.model.wrapper.EntityWrapper;
import com.backendless.test.core.pool.EntityPool;
import com.backendless.test.core.service.ExampleService;
import com.backendless.test.core.service.RemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {
    private final RemoteService remoteService;
    private final EntityPool entityPool;


    @Override
    public EntityExample createEntity(EntityExample example) {
        EntityExample created = remoteService.createAndGet(example);
        if (entityPool.get(created.getClass().getSimpleName()) != null)
            entityPool.get(created.getClass().getSimpleName()).add(new EntityWrapper(created, created.getId(), System.currentTimeMillis()));
        else
            entityPool.put(created.getClass().getSimpleName(), Arrays.asList(new EntityWrapper(created, created.getId(), System.currentTimeMillis())));
        return created;
    }

    @Override
    public EntityExample getExampleById(String id) {
        return remoteService.getById(EntityExample.class, id);
    }

    @Override
    public List<EntityExample> getAllExamples() {
        return remoteService.getList(EntityExampleList.class, null).getItems();
    }

    @Override
    public EntityExample updateExample(EntityExample example) {
        EntityExample updated = remoteService.updateAndGet(example);
        List<EntityWrapper> entityWrapperList = entityPool.get(EntityExample.class.getSimpleName());
        EntityWrapper wrapper = entityWrapperList.get(entityWrapperList.indexOf(new EntityWrapper(updated, null, null)));
        wrapper.setModified(System.currentTimeMillis());
        entityWrapperList.set(entityWrapperList.indexOf(wrapper), wrapper);
        return updated;
    }

    @Override
    public void deleteExample(EntityExample example) {
        remoteService.deleteById(example.getClass(), example.getId());
    }
}
