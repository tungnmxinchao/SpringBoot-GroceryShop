package org.grocery.groceryshop.mapper;

import org.grocery.groceryshop.dto.request.OrderDetailRequest;
import org.grocery.groceryshop.entity.OrderDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {
    List<OrderDetails> toOrderDetails(List<OrderDetailRequest> orderDetailsList);
}
