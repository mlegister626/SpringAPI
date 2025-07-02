package com.codewithmosh.users;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@AllArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Iterable<UserDto> returnAllUsers(String sortBy){
        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getSingleUser(Long id){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("A user could not be found"));
        return userMapper.toDto(user);
    }

    public UserDto registerUser(RegisterUserRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserExistsException("The email is already registered.");
        }
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
    public UserDto updateUser(UpdateUserRequest request, Long id){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("A user could not be found"));
        userMapper.update(request, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public void deleteUser(Long id){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("A user could not be found"));
        userRepository.delete(user);
    }

    public void changePassword(Long id, ChangePasswordRequest request){
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("A user could not be found"));
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new UserNotFoundException("The old password does not match the current password.");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
