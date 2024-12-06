package org.example.customer.dto.mapper;

import org.example.customer.domain.Customer;
import org.example.customer.dto.RequestCustomerDTO;
import org.example.order.dto.mapper.RequestOrderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RequestOrderMapper.class)
public interface RequestCustomerMapper {
    @Mapping(target = "user.id", source = "userId")
    Customer toEntity(RequestCustomerDTO requestCustomerDTO);
}
