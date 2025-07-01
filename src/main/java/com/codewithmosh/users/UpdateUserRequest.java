package com.codewithmosh.users;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
