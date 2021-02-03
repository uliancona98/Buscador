package mx.uady.appbusqueda.model;

import mx.uady.appbusqueda.model.*;
import mx.uady.appbusqueda.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostConstructBean {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void init(){

        List<ProductEntity> productEntityList = new ArrayList<>();
        for(int i = 0; i<100; i++){
            ProductEntity product = new ProductEntity();
            product.setCode(String.valueOf(i));
            product.setDescription("Description of Product "+i);
            product.setName("Name "+i);
            product.setPrice(i+10.00);
            product.setStock(i+2);
            productEntityList.add(product);
        }

        productRepository.saveAll(productEntityList);

        Iterable<ProductEntity> pro=  productRepository.findAll();
        pro.forEach(System.out::println);
    }
}