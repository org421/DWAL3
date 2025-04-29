package web.projetdevwebavancer.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.TableRestaurant;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.TableRestaurantRepository;
import web.projetdevwebavancer.Service.ReservationRestaurantService;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

@RestController
public class RestReservationController {

    @Autowired
    ReservationRestaurantService reservationRestaurantService;

    // retrieves available reservation dates for a specific restaurant, time, and number of people
    @GetMapping("/reservationTable")
    public List<LocalDate> getReservationTable(@RequestParam("id") Long idRestaurant, @RequestParam("nombrePersonne") int NbPersonne, @RequestParam("horraireTable") String horraire, @RequestParam("moiscalendrier") int mois, @RequestParam("anneecalendrier") int annee) {
        System.out.println("id Restaurant ==> " + idRestaurant + " Nb personnes ==> " + NbPersonne + " Horraire ==> " + horraire);
       List<LocalDate> dateDisponnible = new ArrayList<>();
        dateDisponnible=reservationRestaurantService.obtenirDatesAvecReservations(mois, annee, idRestaurant, NbPersonne,horraire);
        return dateDisponnible;

    }
}
