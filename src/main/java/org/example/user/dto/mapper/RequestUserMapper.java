package org.example.user.dto.mapper;

import org.example.customer.dto.mapper.RequestCustomerMapper;
import org.example.user.domain.User;
import org.example.user.dto.RequestUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RequestCustomerMapper.class)
public interface RequestUserMapper {
    User toEntity(RequestUserDTO requestUserDTO);
}
