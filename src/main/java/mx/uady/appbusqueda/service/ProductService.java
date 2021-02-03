package mx.uady.appbusqueda.service;

import mx.uady.appbusqueda.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    public Page<ProductEntity> findAllProducts(Pageable pageable);
}