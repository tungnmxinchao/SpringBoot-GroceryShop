package org.grocery.groceryshop.repository;

import org.grocery.groceryshop.dto.response.UserResponse;
import org.grocery.groceryshop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT new org.grocery.groceryshop.dto.response.UserResponse(u.userId, u.username, COALESCE(CAST(SUM(i.amount) AS BigDecimal), 0)) " +
            "FROM Users u " +
            "LEFT JOIN Orders o ON u.userId = o.user.userId " +
            "LEFT JOIN Invoices i ON o.orderId = i.order.orderId " +
            "GROUP BY u.userId, u.username " +
            "ORDER BY COALESCE(CAST(SUM(i.amount) AS BigDecimal), 0) DESC")
    List<UserResponse> findTopSpender();


    boolean existsByUsername(String username);

    Optional<Users> findByUsername(String username);
}
