package org.example.user.dto.mapper;

import org.example.customer.dto.mapper.ResponseCustomerMapper;
import org.example.user.domain.User;
import org.example.user.dto.ResponseUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResponseCustomerMapper.class)
public interface ResponseUserMapper {
    ResponseUserDTO toDTO(User user);

    List<ResponseUserDTO> toDTOs(List<User> users);
}
