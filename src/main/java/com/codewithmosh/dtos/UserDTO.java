package com.codewithmosh.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    @JsonProperty("user_id")
    private Long id;
    private String name;
    private String email;
    private String password;

}
