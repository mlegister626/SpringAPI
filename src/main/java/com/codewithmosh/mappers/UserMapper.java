package com.codewithmosh.mappers;

import com.codewithmosh.dtos.UserDto;
import com.codewithmosh.entities.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
