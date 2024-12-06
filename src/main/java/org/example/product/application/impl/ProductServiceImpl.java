package org.example.product.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.product.application.ProductService;
import org.example.product.data.ProductRepository;
import org.example.product.dto.RequestProductDTO;
import org.example.product.dto.ResponseProductDTO;
import org.example.product.dto.mapper.RequestProductMapper;
import org.example.product.dto.mapper.ResponseProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ResponseProductMapper responseProductMapper;
    private final RequestProductMapper requestProductMapper;

    @Override
    @Transactional
    public ResponseProductDTO create(RequestProductDTO requestProductDTO) {
        return responseProductMapper.toDTO(productRepository.save(requestProductMapper.toEntity(requestProductDTO)));
    }

    @Override
    @Transactional
    public ResponseProductDTO read(Long id) {
        return productRepository.findById(id)
                .map(responseProductMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    @Transactional
    public ResponseProductDTO update(Long id, RequestProductDTO requestProductDTO) {
        productRepository.update(id, requestProductDTO.productName(), requestProductDTO.price());
        return productRepository.findById(id)
                .map(responseProductMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<ResponseProductDTO> readAll() {
        return responseProductMapper.toDTOs(productRepository.findAll());
    }
}
