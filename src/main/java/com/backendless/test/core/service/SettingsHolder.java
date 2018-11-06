package com.backendless.test.core.service;

import com.backendless.test.core.model.Settings;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
public interface SettingsHolder {
    Settings getSettings();

    Settings updateSettings(Settings request) throws Exception;
}
