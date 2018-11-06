package com.backendless.test.core.controllers;

import com.backendless.test.core.model.Settings;
import com.backendless.test.core.service.SettingsHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class SettingsController {
    private final SettingsHolder settingsHolder;


    @CrossOrigin
    @RequestMapping(value = "/settings", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object getSettings() throws Exception {
        return settingsHolder.getSettings();
    }

    @CrossOrigin
    @RequestMapping(value = "/settings", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object updateSettings(@RequestBody Settings request) throws Exception {
        return settingsHolder.updateSettings(request);
    }
}
