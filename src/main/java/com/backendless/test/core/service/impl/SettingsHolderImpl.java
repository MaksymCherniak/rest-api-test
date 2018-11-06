package com.backendless.test.core.service.impl;

import com.backendless.test.core.model.Settings;
import com.backendless.test.core.service.SettingsHolder;
import com.backendless.test.core.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Component
@Scope("singleton")
@RequiredArgsConstructor
public class SettingsHolderImpl implements SettingsHolder {
    private final Environment environment;
    private Settings settings;


    @PostConstruct
    private void setUP() {
        settings = new Settings();
        settings.setHostName(environment.getProperty("remote.default.host"));
        settings.setLogin(environment.getProperty("remote.default.user.login"));
        settings.setPassword(environment.getProperty("remote.default.user.password"));
        settings.setUseSsl(Boolean.valueOf(environment.getProperty("remote.default.use.ssl")));
        settings.setApplicationId(environment.getProperty("remote.default.application.id"));
        settings.setRestApiKey(environment.getProperty("remote.default.rest.api.key"));
        settings.setCleanUpTimePeriod(Long.parseLong(environment.getProperty("default.cleanup.period")));
        settings.setCleanUpTimeUnit(TimeUnit.valueOf(environment.getProperty("default.cleanup.time.unit")));

        try {
            settings.setService(RestUtils.initWebResource(settings));
        } catch (Exception e) {
            System.out.println("WebResource didn't init. Cause: " + e.getMessage());
        }
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public Settings updateSettings(Settings request) throws Exception {
        settings.setHostName(request.getHostName());
        settings.setLogin(request.getLogin());
        settings.setPassword(request.getPassword());
        settings.setUseSsl(request.isUseSsl());
        settings.setApplicationId(request.getApplicationId());
        settings.setRestApiKey(request.getRestApiKey());
        settings.setCleanUpTimeUnit(request.getCleanUpTimeUnit());
        settings.setCleanUpTimePeriod(request.getCleanUpTimePeriod());
        settings.setService(RestUtils.initWebResource(settings));
        return settings;
    }
}
