package org.grocery.groceryshop.repository;

import org.grocery.groceryshop.dto.response.ProductResponse;
import org.grocery.groceryshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.grocery.groceryshop.entity.OrderDetails;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory_CategoryID(Long categoryId, Pageable pageable);

    Page<Product> findByStatus(int status, Pageable pageable);


}
