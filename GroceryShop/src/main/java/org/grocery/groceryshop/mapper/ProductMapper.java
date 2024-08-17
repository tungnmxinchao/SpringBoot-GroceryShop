package org.grocery.groceryshop.mapper;

import org.grocery.groceryshop.dto.request.ProductRequest;
import org.grocery.groceryshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    void updateProduct(@MappingTarget Product product, ProductRequest productRequest);
}
