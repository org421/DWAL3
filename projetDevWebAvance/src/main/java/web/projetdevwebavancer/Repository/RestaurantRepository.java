package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    Restaurant findByid(int id);

    List<Restaurant> findAllByLatitudeBetweenAndLongitudeBetween(float latitude, float longitude, float latitude2, float longitude2);

    Restaurant findOneByIdAndUuid(int id, String uuid);
}
