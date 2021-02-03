package mx.uady.appbusqueda.repository;


import mx.uady.appbusqueda.model.*;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,Integer> {
}