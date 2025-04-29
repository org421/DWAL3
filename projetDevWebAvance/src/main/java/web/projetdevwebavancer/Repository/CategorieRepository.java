package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Carte;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Plat;

import java.util.List;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    List<Categorie> findByCarte(Carte carte);
}
