package org.example.lineItem.dto.Mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.lineItem.domain.LineItem;
import org.example.lineItem.dto.RequestLineItemDTO;
import org.example.product.domain.Product;
import org.example.product.dto.mapper.RequestProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RequestProductMapper.class)
public abstract class RequestLineItemMapper {
    @PersistenceContext
    private EntityManager entityManager;

    @Mapping(target = "product", source = "productId", qualifiedByName = "getReference")
    public abstract LineItem toEntity(RequestLineItemDTO requestLineItemDTO);

    public abstract List<LineItem> toEntities(List<RequestLineItemDTO> requestLineItemDTOList);

    @Named("getReference")
    protected Product getReference(Long id){
        return entityManager.getReference(Product.class, id);
    }
}
