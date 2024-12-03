package org.example.product.application;

import org.example.product.dto.RequestProductDTO;
import org.example.product.dto.ResponseProductDTO;

import java.util.List;

public interface ProductService {
    ResponseProductDTO create(RequestProductDTO requestProductDTO);

    ResponseProductDTO read(Long id);

    ResponseProductDTO update(Long id, RequestProductDTO requestProductDTO);

    void delete(Long id);

    List<ResponseProductDTO> readAll();
}
