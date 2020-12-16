package mx.uady.sicei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mx.uady.sicei.model.TokenBlacklist;

@Repository
public interface TokenRepository extends CrudRepository<TokenBlacklist, Integer> {
    TokenBlacklist findByToken(String token);
}