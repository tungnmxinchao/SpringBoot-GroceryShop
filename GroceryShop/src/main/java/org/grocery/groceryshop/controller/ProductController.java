package org.grocery.groceryshop.controller;

import jakarta.validation.Valid;
import org.grocery.groceryshop.dto.request.ProductRequest;
import org.grocery.groceryshop.dto.response.ApiResponse;
import org.grocery.groceryshop.dto.response.ProductResponse;
import org.grocery.groceryshop.entity.Product;
import org.grocery.groceryshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    Page<Product> getProducts(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return productService.getPaginatedList(pageable);
    }

    @GetMapping("/public")
    Page<Product> getPublicProducts(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return productService.getPublicProducts(pageable);
    }

    @GetMapping("/category/{categoryID}")
    public Page<Product> getProducts(@PathVariable String categoryID,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productService.getProductsByCategory(categoryID, pageable);
    }

    @PostMapping
    public ApiResponse<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Add product successfully!");
        apiResponse.setResult(productService.createProduct(productRequest));
        return apiResponse;
    }

    @GetMapping("/id/{productID}")
    public Product getProduct(@PathVariable("productID") String productID){
        return productService.getProduct(productID);
    }

    @PutMapping("/delete/{productID}")
    public ApiResponse<Product> deleteProduct(@PathVariable("productID") String productID) {
        Product product = productService.deleteProduct(productID);
        ApiResponse<Product> response = new ApiResponse<>();
        response.setMessage("Delete successfully!");
        response.setResult(product);
        return response;
    }

    @PutMapping("/update/{productID}")
    public ApiResponse<Product> updateProduct(@Valid @RequestBody ProductRequest productRequest,
                                              @PathVariable("productID") String productID) {
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Update product successfully!");
        apiResponse.setResult(productService.updateProduct(productRequest, productID));
        return apiResponse;
    }


    @GetMapping("/top-selling")
    public List<ProductResponse> getTopSellingProducts() {
        List<ProductResponse> products = productService.getTopSellingProducts();
        logger.info("API Response: {}", products);
        return products;
    }
}
