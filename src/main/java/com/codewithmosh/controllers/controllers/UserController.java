package com.codewithmosh.controllers.controllers;

import com.codewithmosh.dtos.ChangePasswordRequest;
import com.codewithmosh.dtos.RegisterUserRequest;
import com.codewithmosh.dtos.UpdateUserRequest;
import com.codewithmosh.dtos.UserDto;
import com.codewithmosh.mappers.UserMapper;
import com.codewithmosh.repositories.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")

public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @GetMapping("")
    //method: Get Post, Put, Delete
    public Iterable<UserDto> getAllUsers(@RequestHeader(name = "x-auth-token") String authToken,
                                         @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy){
       if(!Set.of("name","email").contains(sortBy)){
            sortBy = "name";
        }
         return userRepository.findAll(Sort.by(sortBy))
                 .stream()
                 .map(userMapper::toDto)
                 .toList();
    }
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
       var user = userRepository.findById(id).orElse(null);
       if(user == null){
           return ResponseEntity.notFound().build();
       }
       var userDto = new UserDto(user.getId(),user.getName(), user.getName(), null);
       return ResponseEntity.ok(userMapper.toDto(user));
    }
    @PostMapping("")
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder){
        var user = userMapper.toEntity(request);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") long id,@RequestBody UpdateUserRequest request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){

            return ResponseEntity.notFound().build();
        }

        userMapper.update(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable long id, @RequestBody ChangePasswordRequest request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

}
