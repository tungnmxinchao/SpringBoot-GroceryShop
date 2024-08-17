package org.grocery.groceryshop.controller;

import org.grocery.groceryshop.dto.request.UserCreationRequest;
import org.grocery.groceryshop.dto.response.ApiResponse;
import org.grocery.groceryshop.dto.response.UserResponse;
import org.grocery.groceryshop.entity.Product;
import org.grocery.groceryshop.entity.Users;
import org.grocery.groceryshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/top-spender")
    public List<UserResponse> getTopSpender() {
        return userService.getTopCustomer();
    }

    @PostMapping
    public ApiResponse<Users> createUser(@RequestBody UserCreationRequest userCreationRequest){
        ApiResponse<Users> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Create User Successfully!");
        apiResponse.setResult(userService.createUser(userCreationRequest));
        return apiResponse;
    }

    @GetMapping
    public Page<Users> getUsers(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return userService.getUsersList(pageable);
    }

    @GetMapping("/id/{userId}")
    public Users getUserById(@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }

    @PutMapping("/delete/{userId}")
    public ApiResponse<Users> deleteProduct(@PathVariable("userId") String userId) {
        Users user = userService.deleteUser(userId);
        ApiResponse<Users> response = new ApiResponse<>();
        response.setMessage("Delete successfully!");
        response.setResult(user);
        return response;
    }

    @PutMapping("/activeUser/{userId}")
    public ApiResponse<Users> activeUser(@PathVariable("userId") String userId) {
        Users user = userService.activeUser(userId);
        ApiResponse<Users> response = new ApiResponse<>();
        response.setMessage("Active successfully!");
        response.setResult(user);
        return response;
    }
}
