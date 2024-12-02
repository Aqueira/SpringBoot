package org.example.order.dto.mapper;

import org.example.lineItem.dto.Mapper.RequestLineItemMapper;
import org.example.order.domain.Order;
import org.example.order.dto.RequestOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RequestLineItemMapper.class})
public interface RequestOrderMapper {
    @Mapping(target = "customer.id", source = "customerId")
    Order toEntity(RequestOrderDTO requestOrderDTO);

    List<Order> toEntities(List<RequestOrderDTO> requestOrderDTOs);
}
