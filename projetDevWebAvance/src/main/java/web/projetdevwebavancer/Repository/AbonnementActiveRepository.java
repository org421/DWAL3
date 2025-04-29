package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.AbonnementActive;
import web.projetdevwebavancer.Entity.Restaurateur;

public interface AbonnementActiveRepository extends JpaRepository<AbonnementActive, Long> {
    AbonnementActive findByRestaurateur(Restaurateur restaurateur);
}
