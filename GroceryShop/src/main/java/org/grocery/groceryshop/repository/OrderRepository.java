package org.grocery.groceryshop.repository;

import org.grocery.groceryshop.dto.response.CustomerResponse;
import org.grocery.groceryshop.dto.response.OrdersResponse;
import org.grocery.groceryshop.entity.Orders;
import org.grocery.groceryshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT new org.grocery.groceryshop.dto.response.OrdersResponse(o.status, COUNT(*)) " +
            "FROM Orders o " +
            "GROUP BY o.status")
    List<OrdersResponse> findAllOrderStatusCounts();

    public Page<Orders> findByUser_UserId(Long categoryId, Pageable pageable);


}
