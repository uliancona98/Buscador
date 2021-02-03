package mx.uady.appbusqueda.service;

import mx.uady.appbusqueda.model.*;
import mx.uady.appbusqueda.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductEntity> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }
}