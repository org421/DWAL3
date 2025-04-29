package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.TableRestaurant;

import java.util.List;

public interface TableRestaurantRepository extends JpaRepository<TableRestaurant, Long> {
    TableRestaurant findOneByRestaurantIdAndNombrePersonnesMax(Long restaurantId, int nombrePersonnes);
}
