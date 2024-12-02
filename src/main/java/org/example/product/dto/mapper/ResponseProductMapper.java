package org.example.product.dto.mapper;

import org.example.product.domain.Product;
import org.example.product.dto.ResponseProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResponseProductMapper {
    @Mapping(target = "lineItemId", source = "lineItem.id")
    ResponseProductDTO toDTO(Product product);

    List<ResponseProductDTO> toDTOs(List<Product> products);
}
