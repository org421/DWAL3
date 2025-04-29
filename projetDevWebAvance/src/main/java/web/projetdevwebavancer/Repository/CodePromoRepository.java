package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.CodePromo;

public interface CodePromoRepository extends JpaRepository<CodePromo, Long> {

    CodePromo findByCode(String code);
}
