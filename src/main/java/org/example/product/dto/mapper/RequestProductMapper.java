package org.example.product.dto.mapper;

import org.example.product.domain.Product;
import org.example.product.dto.RequestProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestProductMapper {
    Product toEntity(RequestProductDTO requestProductDTO);
}
