package com.backendless.test.core.utils;

import com.backendless.test.core.exceptions.ApiError;
import com.backendless.test.core.exceptions.ApiException;
import com.backendless.test.core.model.Settings;
import com.backendless.test.core.model.users.LoginRequest;
import com.backendless.test.core.model.users.LoginResponse;
import com.backendless.test.core.model.users.RegisterRequest;
import com.backendless.test.core.model.users.RegisterResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import javax.ws.rs.core.MediaType;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
public class RestUtils {
    private final static String USERS_REGISTER = "/users/register";
    private final static String USERS_LOGIN = "/users/login";
    private final static String USER_TOKEN_HEADER = "user-token";


    public static WebResource initWebResource(Settings settings) {
        SSLContext context = null;
        WebResource service;
        if (settings.isUseSsl()) {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            try {
                context = SSLContext.getInstance("SSL");
                context.init(null, trustAllCerts, null);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
                throw new ApiException("couldn't initialize SSL: " + e.getMessage());
            }
        }

        // turn the hostname verification off.
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        });

        ClientConfig config = new DefaultClientConfig();
        if (settings.isUseSsl()) {
            config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, context));
            service = Client.create(config).resource("https://" + settings.getHostName() + "/" + settings.getApplicationId() + "/" + settings.getRestApiKey());
        } else {
            service = Client.create(config).resource("http://" + settings.getHostName() + "/" + settings.getApplicationId() + "/" + settings.getRestApiKey());
        }
        register(service, settings);
        return service;
    }

    public static void handleErrors(ClientResponse response) throws ApiException {
        try {
            ApiError error = response.getEntity(ApiError.class);
            if (StringUtils.isNotBlank(error.getMessage())) {
                throw new ApiException(error.getCode() + ": " + error.getMessage());
            } else {
                ClientResponse.Status status = response.getClientResponseStatus();
                throw new ApiException(response.getStatus() + (status != null ? " " + status.getReasonPhrase() : ""));
            }
        } catch (ApiException apiEx) {
            throw apiEx;
        } catch (Exception e) {
            ClientResponse.Status status = response.getClientResponseStatus();
            throw new ApiException(response.getStatus() + (status != null ? " " + status.getReasonPhrase() : ""));
        }
    }


    private static void register(WebResource service, Settings setting) {
        try {
            RegisterResponse response = service.path(USERS_REGISTER).
                    type(MediaType.APPLICATION_JSON_TYPE).post(RegisterResponse.class, new RegisterRequest(setting.getLogin(), setting.getPassword()));
            login(service, setting);
        } catch (UniformInterfaceException e) {
            ApiError error = e.getResponse().getEntity(ApiError.class);
            switch (error.getCode()) {
                case "3009":
                    return;
                case "3033":
                    login(service, setting);
                    break;
                default:
                    handleErrors(e.getResponse());
            }
        }
    }

    private static void login(WebResource service, Settings setting) {
        try {
            LoginResponse response = service.path(USERS_LOGIN).
                    type(MediaType.APPLICATION_JSON_TYPE).post(LoginResponse.class, new LoginRequest(setting.getLogin(), setting.getPassword()));
            service.header(USER_TOKEN_HEADER, response.getUserToken());
        } catch (UniformInterfaceException e) {
            ApiError error = e.getResponse().getEntity(ApiError.class);
            switch (error.getCode()) {
                case "3000":
                    return;
                default:
                    handleErrors(e.getResponse());
            }
        }
    }
}
