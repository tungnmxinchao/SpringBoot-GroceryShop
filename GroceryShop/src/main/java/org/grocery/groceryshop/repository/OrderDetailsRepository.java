package org.grocery.groceryshop.repository;

import org.grocery.groceryshop.dto.response.CategoryResponse;
import org.grocery.groceryshop.dto.response.ProductResponse;
import org.grocery.groceryshop.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    @Query("SELECT new org.grocery.groceryshop.dto.response.ProductResponse(p.productId, p.name, COALESCE(CAST(SUM(od.quantity) AS long), 0L)) " +
            "FROM Product p " +
            "LEFT JOIN OrderDetails od ON p.productId = od.product.productId " +
            "GROUP BY p.productId, p.name " +
            "ORDER BY COALESCE(CAST(SUM(od.quantity) AS long), 0L) DESC")
    List<ProductResponse> findTopSellingProducts();


    @Query("SELECT new org.grocery.groceryshop.dto.response.CategoryResponse(c.categoryID, c.categoryName, COALESCE(CAST(SUM(od.quantity) AS long), 0L)) " +
            "FROM Category c " +
            "LEFT JOIN c.products p " +
            "LEFT JOIN OrderDetails od ON od.product = p " +
            "GROUP BY c.categoryID, c.categoryName " +
            "ORDER BY COALESCE(CAST(SUM(od.quantity) AS long), 0L) DESC")
    List<CategoryResponse> findTopSellingCategories();


}
