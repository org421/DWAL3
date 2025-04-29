package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCategorieId(Long categorieId);
}

