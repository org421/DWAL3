package web.projetdevwebavancer.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Facture;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
//    List<Facture> findByidClient(int idCliente);
}
