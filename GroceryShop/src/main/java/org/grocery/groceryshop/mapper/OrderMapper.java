package org.grocery.groceryshop.mapper;

import org.grocery.groceryshop.dto.request.OrderRequest;
import org.grocery.groceryshop.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Orders toOder (OrderRequest orderRequest);

}
