package org.grocery.groceryshop.controller;

import org.grocery.groceryshop.dto.response.CategoryResponse;
import org.grocery.groceryshop.entity.Category;
import org.grocery.groceryshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getListCategory(){
        return categoryService.getCategoryList();
    }

    @GetMapping("/top-selling")
    public List<CategoryResponse> getTopSellingCategories() {
        return categoryService.getTopSellingCategories();
    }
}
