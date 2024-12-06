package org.example.product.dto.mapper;

import org.example.product.domain.Product;
import org.example.product.dto.ResponseProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResponseProductMapper {
    ResponseProductDTO toDTO(Product product);
    List<ResponseProductDTO> toDTOs(List<Product> products);
}
