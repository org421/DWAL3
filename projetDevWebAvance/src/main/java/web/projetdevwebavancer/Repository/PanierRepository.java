package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Panier;

public interface PanierRepository extends JpaRepository<Panier, Long> {
}
