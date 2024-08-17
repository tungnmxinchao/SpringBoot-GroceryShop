package org.grocery.groceryshop.mapper;

import org.grocery.groceryshop.dto.request.UserCreationRequest;
import org.grocery.groceryshop.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserCreationRequest userCreationRequest);
}
