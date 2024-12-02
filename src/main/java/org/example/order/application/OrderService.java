package org.example.order.application;

import org.example.order.dto.RequestOrderDTO;
import org.example.order.dto.ResponseOrderDTO;

import java.util.List;


public interface OrderService {
    ResponseOrderDTO create(RequestOrderDTO entity);

    ResponseOrderDTO read(Long id);

    ResponseOrderDTO update(Long id, RequestOrderDTO entity);

    void delete(Long id);

    List<ResponseOrderDTO> findAll();

}
