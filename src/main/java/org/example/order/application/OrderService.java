package org.example.order.application;

import org.example.order.dto.RequestOrderDTO;
import org.example.order.dto.ResponseOrderDTO;

import java.util.List;
import java.util.Optional;


public interface OrderService {
    ResponseOrderDTO create(RequestOrderDTO entity);

    Optional<ResponseOrderDTO> read(Long id);

    void update(Long id, RequestOrderDTO entity);

    void delete(Long id);

    List<ResponseOrderDTO> findAll();

}
