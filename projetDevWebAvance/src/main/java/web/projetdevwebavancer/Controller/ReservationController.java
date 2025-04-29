package web.projetdevwebavancer.Controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.TableReserver;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.TableReserverRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.ReservationRestaurantService;
import web.projetdevwebavancer.Service.RestaurantService;
import web.projetdevwebavancer.Service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    ReservationRestaurantService reservationRestaurantService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    TableReserverRepository tableReserverRepository;

    // books a table at a restaurant if available
    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public String reservation(@RequestParam("nbPersonne") int nbPersonne, @RequestParam("horaire") String horaire, @RequestParam("date") String date, @RequestParam("idRestaurant") Long idRestaurant) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            if(reservationRestaurantService.tableDisponible(localDate,nbPersonne,idRestaurant, horaire)){
                reservationRestaurantService.valideReserverTable(localDate,nbPersonne,idRestaurant,horaire,user);
            }

        }
        return "redirect:/";
    }

    // loads the user's reservations and info, and returns the "base" view with "mesReservation" content
    @RequestMapping(value = "/mes-reservations", method = RequestMethod.GET)
    public String mesReservations(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
            List<TableReserver> reservations = tableReserverRepository.findAllByUser(user);
            m.addAttribute("reservations", reservations);
        }
        m.addAttribute("content","mesReservation");
        return "base";
    }
}
