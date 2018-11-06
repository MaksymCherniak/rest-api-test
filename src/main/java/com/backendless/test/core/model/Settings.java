package com.backendless.test.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.jersey.api.client.WebResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@EqualsAndHashCode(exclude = {"service", "isUseSsl", "cleanUpTimePeriod", "cleanUpTimeUnit"})
@ToString(exclude = {"service", "password"})
@JsonIgnoreProperties({"service", "password"})
public class Settings {
    private String hostName;
    private String login;
    private String password;
    private boolean isUseSsl;
    private String applicationId;
    private String restApiKey;

    private Long cleanUpTimePeriod;
    private TimeUnit cleanUpTimeUnit;

    private WebResource service;
}
