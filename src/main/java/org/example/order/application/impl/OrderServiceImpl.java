package org.example.order.application.impl;

import org.example.order.application.OrderService;
import org.example.order.data.OrderRepository;
import org.example.order.dto.RequestOrderDTO;
import org.example.order.dto.ResponseOrderDTO;
import org.example.order.dto.mapper.RequestOrderMapper;
import org.example.order.dto.mapper.ResponseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RequestOrderMapper requestOrderMapper;
    private final ResponseOrderMapper responseOrderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            RequestOrderMapper requestOrderMapper,
                            ResponseOrderMapper responseOrderMapper) {
        this.orderRepository = orderRepository;
        this.requestOrderMapper = requestOrderMapper;
        this.responseOrderMapper = responseOrderMapper;
    }

    @Override
    @Transactional
    public ResponseOrderDTO create(RequestOrderDTO requestOrderDTO) {
        return responseOrderMapper.toDTO(orderRepository.save(requestOrderMapper.toEntity(requestOrderDTO)));
    }

    @Override
    @Transactional
    public ResponseOrderDTO read(Long id) {
        return orderRepository.read(id)
                .map(responseOrderMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseOrderDTO update(Long id, RequestOrderDTO requestOrderDTO) {
        orderRepository.update(id, requestOrderDTO.deliverTo());
        return orderRepository.read(id)
                .map(responseOrderMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    @Transactional
    public List<ResponseOrderDTO> findAll() {
        return responseOrderMapper.toDTOs(orderRepository.readAll());
    }
}
