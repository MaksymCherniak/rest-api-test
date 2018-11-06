package com.backendless.test.core.pool;

import com.backendless.test.core.model.wrapper.EntityWrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Component
public class EntityPool extends ConcurrentHashMap<String, List<EntityWrapper>> {
}
