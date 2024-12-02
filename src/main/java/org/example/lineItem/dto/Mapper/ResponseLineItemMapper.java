package org.example.lineItem.dto.Mapper;

import org.example.lineItem.domain.LineItem;
import org.example.lineItem.dto.ResponseLineItemDTO;
import org.example.product.dto.mapper.ResponseProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResponseProductMapper.class)
public interface ResponseLineItemMapper {
    ResponseLineItemDTO toDTO(LineItem lineItem);

    List<LineItem> toDTOs(List<LineItem> lineItems);
}
