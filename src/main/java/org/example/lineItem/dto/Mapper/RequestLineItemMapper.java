package org.example.lineItem.dto.Mapper;

import org.example.lineItem.domain.LineItem;
import org.example.lineItem.dto.RequestLineItemDTO;
import org.example.order.dto.mapper.RequestOrderMapper;
import org.example.product.dto.mapper.RequestProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RequestProductMapper.class})
public interface RequestLineItemMapper {
    @Mapping(target = "product.id", source = "productId")
    LineItem toEntity(RequestLineItemDTO requestLineItemDTO);

    List<LineItem> toEntities(List<RequestLineItemDTO> requestLineItemDTOList);
}
