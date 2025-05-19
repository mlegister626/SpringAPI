package com.codewithmosh.mappers;

import com.codewithmosh.dtos.RegisterUserRequest;
import com.codewithmosh.dtos.UpdateUserRequest;
import com.codewithmosh.dtos.UserDto;
import com.codewithmosh.entities.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
