package org.example.customer.dto.mapper;

import org.example.customer.domain.Customer;
import org.example.customer.dto.ResponseCustomerDTO;
import org.example.order.dto.mapper.ResponseOrderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResponseOrderMapper.class)
public interface ResponseCustomerMapper {
    @Mapping(target = "userId", source = "user.id")
    ResponseCustomerDTO toDTO(Customer customer);

    List<ResponseCustomerDTO> toDTOs(List<Customer> customers);
}
