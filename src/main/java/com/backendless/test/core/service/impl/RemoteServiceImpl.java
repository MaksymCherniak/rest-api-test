package com.backendless.test.core.service.impl;

import com.backendless.test.core.model.BaseEntity;
import com.backendless.test.core.model.BaseEntityList;
import com.backendless.test.core.service.RemoteService;
import com.backendless.test.core.service.SettingsHolder;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;
import java.util.Objects;

import static com.backendless.test.core.utils.RestUtils.handleErrors;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Service
@RequiredArgsConstructor
public class RemoteServiceImpl implements RemoteService {
    private final SettingsHolder settingsHolder;


    public <T extends BaseEntity> T get(Class<T> entityType, String refURL) {
        try {
            return settingsHolder.getSettings().getService().path(refURL).accept(MediaType.APPLICATION_JSON).get(entityType);
        } catch (UniformInterfaceException e) {
            handleErrors(e.getResponse());
        }
        return null;
    }

    public <T extends BaseEntity> T getById(Class<T> entityType, String id) {
        return get(entityType, entityType.getAnnotation(Path.class).value() + "/" + id);
    }

    public <T extends BaseEntityList> T getList(Class<T> entityType, Map<String, String> searchCriteria) {
        return getList(entityType, searchCriteria, null);
    }

    public <T extends BaseEntityList> T getList(Class<T> entityType, Map<String, String> searchCriteria, String path) {

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();

        if (Objects.nonNull(searchCriteria)) {
            searchCriteria.entrySet().forEach(criteria -> params.add(criteria.getKey(), criteria.getValue()));
        }

        if (StringUtils.isEmpty(path)) {
            path = entityType.getAnnotation(Path.class).value();
        }

        try {
            WebResource r = settingsHolder.getSettings().getService().path(path).queryParams(params);
            return r.accept(MediaType.APPLICATION_JSON).get(entityType);
        } catch (UniformInterfaceException e) {
            handleErrors(e.getResponse());
        }

        return null;
    }

    public <T extends BaseEntity> ClientResponse create(T entity) {
        return create(entity, null);
    }

    public <T extends BaseEntity> ClientResponse create(T entity, String path) {
        if (path == null) {
            path = entity.getClass().getAnnotation(Path.class).value();
        }

        try {
            ClientResponse response = settingsHolder.getSettings().getService().path(path).
                    accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, entity);

            if (response.getStatus() != 200 && response.getStatus() != 201 && response.getStatus() != 202) {
                handleErrors(response);
            }

            return response;
        } catch (UniformInterfaceException e) {
            handleErrors(e.getResponse());
        }

        return null;
    }

    public <T extends BaseEntity> T createAndGet(T entity) {
        return createAndGet(entity, null);
    }

    public <T extends BaseEntity> T createAndGet(T entity, String path) {
        ClientResponse response = (StringUtils.isBlank(path)) ? create(entity) : create(entity, path);

        String idPath = path + "/" + response.getEntity(String.class);

        return getById((Class<T>) entity.getClass(), idPath);
    }

    public <T extends BaseEntity> T updateAndGet(T entity) {
        return null;
    }


    public void delete(String refURL) {
        try {
            settingsHolder.getSettings().getService().path(refURL).delete();
        } catch (UniformInterfaceException e) {
            handleErrors(e.getResponse());
        }
    }

    public void deleteById(Class<? extends BaseEntity> clazz, String id) {
        delete(clazz.getAnnotation(Path.class).value() + "/" + id);
    }

}
