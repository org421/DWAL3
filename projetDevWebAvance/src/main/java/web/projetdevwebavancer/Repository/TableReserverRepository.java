package web.projetdevwebavancer.Repository;

import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.TableReserver;
import web.projetdevwebavancer.Entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TableReserverRepository extends JpaRepository<TableReserver, Long> {
    List<TableReserver> findAllByDateAndIdRestaurantAndNombreDePersonnes(LocalDate date, Long idRestaurant, int nombreDePersonnes);
    List<TableReserver> findAllByUser(User user);
    List<TableReserver> findAllByIdRestaurantAndDateAfterOrIdRestaurantAndDate(Long idRestaurant, LocalDate currentDate, Long idRestaurant2, LocalDate currentDate2);
    int countAllByDateBetweenAndIdRestaurant(LocalDate start, LocalDate end, Long idRestaurant);
    TableReserver findOneByIdAndIdRestaurant(Long idTableReserver, Long idRestaurant);
}
