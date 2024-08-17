package org.grocery.groceryshop.service;

import org.grocery.groceryshop.dto.response.CategoryResponse;
import org.grocery.groceryshop.entity.Category;
import org.grocery.groceryshop.entity.Orders;
import org.grocery.groceryshop.repository.CategoryRepository;
import org.grocery.groceryshop.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getTopSellingCategories() {
        return orderDetailsRepository.findTopSellingCategories();
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

}
