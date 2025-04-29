package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.*;
import web.projetdevwebavancer.Repository.*;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurateurService {

    @Autowired
    RestaurateurRepository restaurateurRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    HoraireRepository horaireRepository;
    @Autowired
    TableRestaurantRepository tableRestaurantRepository;

    @Autowired
    UserService userService;

    // creates a new restaurateur, associates it with a user, creates a new restaurant and its schedule, and updates the user's role to "ROLE_RESTAURATEUR"
    public void createRestaurateur(Restaurateur restaurateur, User user) {
        restaurateur.setUser(user);
        restaurateurRepository.save(restaurateur);
        user.setRestaurateur(restaurateur);
        user.setRole("ROLE_RESTAURATEUR");
        userRepository.save(user);
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurateur(restaurateur);
        restaurant.setUuid(UUID.randomUUID().toString());
        restaurantRepository.save(restaurant);
        Horaire horaire = new Horaire();
        horaire.setRestaurant(restaurant);
        horaireRepository.save(horaire);

        restaurant.setHoraire(horaire);
        restaurateur.setRestaurant(restaurant);
        restaurateurRepository.save(restaurateur);
        restaurantRepository.save(restaurant);

        userService.updateConnexion(user);
    }

    // creates a new table configuration for a restaurant, adding it to the restaurant's list of tables if it doesn't already exist for the specified number of people
    public void creerTable(User user, int nbPersonne, int nbTable) {
        List<TableRestaurant> tables = user.getRestaurateur().getRestaurant().getTables();
        for (TableRestaurant tableRestaurant : tables) {
            if(tableRestaurant.getNombrePersonnesMax() == nbPersonne) {
                return;
            }
        }
        TableRestaurant table = new TableRestaurant();
        table.setNombreTables(nbTable);
        table.setNombrePersonnesMax(nbPersonne);
        table.setRestaurant(user.getRestaurateur().getRestaurant());
        tableRestaurantRepository.save(table);
        user.getRestaurateur().getRestaurant().getTables().add(table);
        restaurantRepository.save(user.getRestaurateur().getRestaurant());
    }
}
