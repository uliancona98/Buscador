package mx.uady.appbusqueda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.uady.appbusqueda.model.TokenBlacklist;

@Repository
public interface TokenRepository extends JpaRepository<TokenBlacklist, Integer> {
    TokenBlacklist findByToken(String token);
}