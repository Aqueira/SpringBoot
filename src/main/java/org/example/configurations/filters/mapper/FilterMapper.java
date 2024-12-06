package org.example.configurations.filters.mapper;

import org.example.user.domain.User;
import org.example.user.dto.ResponseUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilterMapper {
    User responseDTOToEntity(ResponseUserDTO userDTO);
}
