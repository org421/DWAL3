package web.projetdevwebavancer.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestRestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    // searches for restaurants by name (case-insensitive) and returns the list of matching restaurants
    @GetMapping("/searchRestaurant")
    public List<Restaurant> searchRestaurant(@RequestParam("name") String name) {
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(name);
        List<Restaurant> restaurantActif = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getRestaurateur().getAbonnementActive() != null){
                restaurant.setRestaurateur(null);
                restaurant.setHoraire(null);
                restaurantActif.add(restaurant);
            }
        }
        return restaurantActif;
    }

    // finds nearby restaurants within a 1-degree radius of the provided latitude and longitude
    @GetMapping("/locRestaurant")
    public List<Restaurant> locRestaurant(@RequestParam("lat") float lat, @RequestParam("lng") float lng) {
//        List<Restaurant> restaurants = restaurantRepository.findAllByLatitudeBetweenAndLongitudeBetween((float) (lat-1.0), (float) (lng-1.0), (float) (lat+1.0), (float) (lng+1.0));

        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Restaurant> restaurantProche = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            if(restaurant.getLatitude() >= (lat - 1.0) && restaurant.getLatitude() <= (lat + 1.0)   && restaurant.getLongitude() >= (lng- 1.0) && restaurant.getLongitude() <= (lng + 1.0) && restaurant.getRestaurateur().getAbonnementActive() != null) {
                restaurantProche.add(restaurant);
            }
        }
        System.out.println(restaurantProche);
        return restaurantProche;
    }



}
