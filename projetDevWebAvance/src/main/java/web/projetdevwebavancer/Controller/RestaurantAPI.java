package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.TableReserver;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.TableReserverRepository;
import web.projetdevwebavancer.Repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/{uuid}/{id}")
public class RestaurantAPI {

    private final RestaurantRepository restaurantRepository;
    private final TableReserverRepository tableReserverRepository;
    private final UserRepository userRepository;

    public RestaurantAPI(RestaurantRepository restaurantRepository, TableReserverRepository tableReserverRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tableReserverRepository = tableReserverRepository;
        this.userRepository = userRepository;
    }

    // retrieves all future reservations for a specific restaurant
    @RequestMapping(value = "/reservation", method = RequestMethod.GET)
    public List<TableReserver> getAllFuturReservation(@PathVariable String uuid, @PathVariable int id) {
        Restaurant restaurant = restaurantRepository.findOneByIdAndUuid(id,uuid);
        List<TableReserver> reservations = tableReserverRepository.findAllByIdRestaurantAndDateAfterOrIdRestaurantAndDate(restaurant.getId(),LocalDate.now(),restaurant.getId(),LocalDate.now());
        return reservations;
    }

    // deletes a specific reservation for a restaurant
    @RequestMapping(value = "/reservation/{idReservation}", method = RequestMethod.DELETE)
    public void getAllFuturReservation(@PathVariable("uuid") String uuid, @PathVariable("id") int id, @PathVariable("idReservation") Long idReservation) {
        Restaurant restaurant = restaurantRepository.findOneByIdAndUuid(id,uuid);
        if(restaurant != null){
            TableReserver tableReserver = tableReserverRepository.findOneByIdAndIdRestaurant(idReservation,restaurant.getId());
            User user = tableReserver.getUser();
            user.getTableReservers().remove(tableReserver);
            tableReserver.setUser(null);
            userRepository.save(user);
            tableReserverRepository.delete(tableReserver);
        }
    }
}
