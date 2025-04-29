package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.TokenValidation;

public interface TokenValidationRepository extends JpaRepository<TokenValidation, Long> {
    TokenValidation findByToken(String token);
}
