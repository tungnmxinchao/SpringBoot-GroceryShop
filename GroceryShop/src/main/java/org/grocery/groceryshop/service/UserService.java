package org.grocery.groceryshop.service;

import org.grocery.groceryshop.dto.request.UserCreationRequest;
import org.grocery.groceryshop.dto.response.UserResponse;
import org.grocery.groceryshop.entity.Users;
import org.grocery.groceryshop.enums.Role;
import org.grocery.groceryshop.mapper.UserMapper;
import org.grocery.groceryshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    UserMapper userMapper;



    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getTopCustomer() {
        return userRepository.findTopSpender();
    }

    public Users createUser(UserCreationRequest userCreationRequest){

        if(userRepository.existsByUsername(userCreationRequest.getUsername())){
            throw new RuntimeException("Username exist!");
        }

        Users users = userMapper.toUser(userCreationRequest);
        users.setRole("1");
        users.setStatus(1);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        users.setRoles(roles);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        users.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

        return userRepository.save(users);

    }


    public Page<Users> getUsersList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Users getUserById(String userId) {
        try {
            Long id = Long.parseLong(userId);
            return userRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID: " + userId, e);
        }
    }

    public Users deleteUser(String userId) {
        try {
            Long id = Long.parseLong(userId);
            Users user = userRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("User not found with ID: " + userId));
            user.setStatus(0);
            return userRepository.save(user);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID: " + userId, e);
        }
    }

    public Users activeUser(String userId) {
        try {
            Long id = Long.parseLong(userId);
            Users user = userRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("User not found with ID: " + userId));
            user.setStatus(1);
            return userRepository.save(user);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID: " + userId, e);
        }
    }
}
