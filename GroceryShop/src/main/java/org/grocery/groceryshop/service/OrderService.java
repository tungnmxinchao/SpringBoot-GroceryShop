package org.grocery.groceryshop.service;

import org.grocery.groceryshop.dto.request.OrderDetailRequest;
import org.grocery.groceryshop.dto.request.OrderRequest;
import org.grocery.groceryshop.dto.response.OrdersResponse;
import org.grocery.groceryshop.entity.OrderDetails;
import org.grocery.groceryshop.entity.Orders;
import org.grocery.groceryshop.entity.Product;
import org.grocery.groceryshop.entity.Users;
import org.grocery.groceryshop.mapper.OrderDetailsMapper;
import org.grocery.groceryshop.mapper.OrderMapper;
import org.grocery.groceryshop.repository.OrderRepository;
import org.grocery.groceryshop.repository.ProductRepository;
import org.grocery.groceryshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailsMapper orderDetailsMapper;

    @Transactional
    public Orders createOrder(OrderRequest orderRequest) {
        Users user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> productList = new ArrayList<>();
        for (OrderDetailRequest odr : orderRequest.getOrderDetails()){
            Product product = productRepository.findById(odr.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            productList.add(product);
        }

        // Map OrderDetailRequest to OrderDetails and set products
        List<OrderDetails> orderDetails = new ArrayList<>();
        int index = 0;
        for (OrderDetailRequest odr : orderRequest.getOrderDetails()) {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setProduct(productList.get(index));
            orderDetail.setQuantity(odr.getQuantity());

            // Use BigDecimal for price calculation
            BigDecimal priceProduct = productList.get(index).getPrice();
            BigDecimal quantity = BigDecimal.valueOf(odr.getQuantity());
            BigDecimal totalPrice = priceProduct.multiply(quantity);

            orderDetail.setPrice(totalPrice);
            orderDetails.add(orderDetail);

            index++;
        }

        // Map OrderRequest to Order
        Orders orders = new Orders();
        orders.setStatus(1);
        orders.setUser(user);

        BigDecimal totalPriceOrder = BigDecimal.ZERO;
        for (OrderDetails ods : orderDetails) {
            totalPriceOrder = totalPriceOrder.add(ods.getPrice());
        }
        orders.setTotal(totalPriceOrder);

        // Set the order reference in each OrderDetails and add to Orders
        for (OrderDetails orderDetail : orderDetails) {
            orderDetail.setOrder(orders);
        }
        orders.setOrderDetails(orderDetails);

        return orderRepository.save(orders);
    }


    public Page<Orders> getOrderList(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }


    public Orders getOrderByID(String orderID) {
        try {
            Long id = Long.parseLong(orderID);
            return orderRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Order ID: " + orderID, e);
        }
    }

    public Orders updateStatusOrder(String orderID, int status){
        try {
            Long id = Long.parseLong(orderID);
            Orders order = orderRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Order not found with ID: " + orderID));
            order.setStatus(status);
            return orderRepository.save(order);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid order ID: " + orderID, e);
        }

    }

    public List<OrdersResponse> getAllOrderStatusCounts() {
        return orderRepository.findAllOrderStatusCounts();
    }


    public Page<Orders> getOrderByUserID(Long userID, Pageable pageable) {
        return orderRepository.findByUser_UserId(userID, pageable);
    }

    public Orders userTrackByOrderID(Long orderID){
        return orderRepository.findById(orderID).orElse(null);
    }
}
