package com.backendless.test.core.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Maksym Cherniak on 06.11.2018.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequest {
    private String login;
    private String password;
}
