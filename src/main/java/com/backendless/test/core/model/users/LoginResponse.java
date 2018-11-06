package com.backendless.test.core.model.users;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@EqualsAndHashCode
@ToString
public class LoginResponse {
    private String objectId;
    private String userToken;
    private String login;


    @JsonGetter("user-token")
    public String getUserToken() {
        return userToken;
    }
}
