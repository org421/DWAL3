package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Plat;

import java.util.List;

public interface PlatRepository extends JpaRepository<Plat, Long> {
//    List<Plat> findByCategorieId(Long categorieId);
//    List<Plat> findAllByCategorieId(Long categorieId);
}

