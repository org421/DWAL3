package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Carte;
import web.projetdevwebavancer.Entity.Restaurant;

public interface CarteRepository extends JpaRepository<Carte, Long> {
    Carte findByRestaurant(Restaurant restaurant);
}

