package org.example.product.dto.mapper;

import org.example.product.domain.Product;
import org.example.product.dto.RequestProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestProductMapper {
    @Mapping(target = "lineItem.id", source = "lineItemId")
    Product toEntity(RequestProductDTO requestProductDTO);

    List<Product> toEntities(List<RequestProductDTO> requestProductDTOList);
}
