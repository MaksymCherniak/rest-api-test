package com.backendless.test.core.model.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@EqualsAndHashCode
@ToString
public class RegisterResponse {
    private String objectId;
    private String email;
    private String password;
}
