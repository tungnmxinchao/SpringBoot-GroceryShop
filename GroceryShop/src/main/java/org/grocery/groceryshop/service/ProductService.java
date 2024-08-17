package org.grocery.groceryshop.service;

import org.grocery.groceryshop.dto.request.ProductRequest;
import org.grocery.groceryshop.dto.response.ProductResponse;
import org.grocery.groceryshop.entity.Category;
import org.grocery.groceryshop.entity.Product;
import org.grocery.groceryshop.mapper.ProductMapper;
import org.grocery.groceryshop.repository.CategoryRepository;
import org.grocery.groceryshop.repository.OrderDetailsRepository;
import org.grocery.groceryshop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    public Page<Product> getPaginatedList(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Page<Product> getProductsByCategory(String categoryID, Pageable pageable) {
        try {
            Long id = Long.parseLong(categoryID);

            return productRepository.findByCategory_CategoryID(id, pageable);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid category ID: " + categoryID, e);
        }
    }

    @Transactional
    public Product createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productMapper.toProduct(productRequest);
        product.setStatus(1);
        product.setCategory(category);

        return productRepository.save(product);
    }


    public Product getProduct(String productID) {
        try {
            Long id = Long.parseLong(productID);
            return productRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid product ID: " + productID, e);
        }
    }

    public Product deleteProduct(String productID) {
        try {
            Long id = Long.parseLong(productID);
            Product product = productRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Product not found with ID: " + productID));
            product.setStatus(0);
            return productRepository.save(product);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid product ID: " + productID, e);
        }
    }


    public Product updateProduct(ProductRequest productRequest, String productID) {
        try {
            Long id = Long.parseLong(productID);
            Product product = productRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Product not found with ID: " + productID));

            productMapper.updateProduct(product, productRequest);

            return productRepository.save(product);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid product ID: " + productID, e);
        }
    }

    public List<ProductResponse> getTopSellingProducts() {
        List<ProductResponse> products = orderDetailsRepository.findTopSellingProducts();
        logger.info("Top Selling Products: {}", products);
        return products;
    }

    public Page<Product> getPublicProducts(Pageable pageable) {
        return productRepository.findByStatus(1, pageable);
    }
}
