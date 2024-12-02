package org.example.order.dto.mapper;

import org.example.lineItem.dto.Mapper.ResponseLineItemMapper;
import org.example.order.domain.Order;
import org.example.order.dto.ResponseOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResponseLineItemMapper.class)
public interface ResponseOrderMapper {
    @Mapping(target = "customerId", source = "customer.id")
    ResponseOrderDTO toDTO(Order order);

    List<ResponseOrderDTO> toDTOs(List<Order> orders);
}
