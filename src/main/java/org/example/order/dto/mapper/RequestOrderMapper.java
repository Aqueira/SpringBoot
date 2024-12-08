package org.example.order.dto.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.customer.domain.Customer;
import org.example.lineItem.dto.Mapper.RequestLineItemMapper;
import org.example.order.domain.Order;
import org.example.order.dto.RequestOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RequestLineItemMapper.class)
public abstract class RequestOrderMapper {
    @PersistenceContext
    private EntityManager entityManager;

    @Mapping(target = "customer", source = "customerId", qualifiedByName = "getReference")
    public abstract Order toEntity(RequestOrderDTO requestOrderDTO);

    public abstract List<Order> toEntities(List<RequestOrderDTO> requestOrderDTOs);

    @Named("getReference")
    protected Customer toReference(Long id) {
        return entityManager.getReference(Customer.class, id);
    }
}
