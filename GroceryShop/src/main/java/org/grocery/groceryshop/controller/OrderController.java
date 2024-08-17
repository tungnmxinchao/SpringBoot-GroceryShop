package org.grocery.groceryshop.controller;


import jakarta.validation.Valid;
import org.grocery.groceryshop.dto.request.OrderRequest;
import org.grocery.groceryshop.dto.response.OrdersResponse;
import org.grocery.groceryshop.dto.response.ApiResponse;
import org.grocery.groceryshop.entity.Orders;
import org.grocery.groceryshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping()
    public Orders createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping
    public Page<Orders> getOrderList(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return orderService.getOrderList(pageable);

    }
    @GetMapping("/all-status-counts")
    public ResponseEntity<List<OrdersResponse>> getAllOrderStatusCounts() {
        List<OrdersResponse> response = orderService.getAllOrderStatusCounts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderID}")
    public Orders getOrderById(@PathVariable("orderID") String orderID){
        return orderService.getOrderByID(orderID);
    }

    @PutMapping("/update/{orderID}")
    public ApiResponse<Orders> updateStatusOrder(@PathVariable("orderID") String orderID, @RequestParam int status){
        ApiResponse<Orders> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Update order successfully!");
        apiResponse.setResult(orderService.updateStatusOrder(orderID, status));
        return apiResponse;
    }

    @GetMapping("/user/{userID}")
    public Page<Orders> getOrderByUserID(@PathVariable("userID") Long userID, @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return orderService.getOrderByUserID(userID, pageable);
    }

    @GetMapping("/user/trackOrder/{orderID}")
    public Orders userTrackingByOrderID(@PathVariable("orderID") Long orderID){
        return orderService.userTrackByOrderID(orderID);
    }


}
