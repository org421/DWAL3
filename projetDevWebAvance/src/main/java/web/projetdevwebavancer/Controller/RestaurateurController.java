package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.*;
import web.projetdevwebavancer.Repository.HoraireRepository;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.TableRestaurantRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.ReservationRestaurantService;
import web.projetdevwebavancer.Service.RestaurantService;
import web.projetdevwebavancer.Service.RestaurateurService;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;

@Controller
public class RestaurateurController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurateurService restaurateurService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    TableRestaurantRepository tableRestaurantRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    private ReservationRestaurantService reservationRestaurantService;

    // displays the professional account creation page and loads authenticated user info
    @RequestMapping(value = "/Creation-compte-pro", method = RequestMethod.GET)
    public String creationPro(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }



        m.addAttribute("content", "creationPro");
        return "base";
    }

    // creates a new professional account with the provided SIRET
    @RequestMapping(value = "/Creation-compte-pro", method = RequestMethod.POST)
    public String creationProPost(@RequestParam("siret") String siret) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Restaurateur restaurateur = new Restaurateur();
            restaurateur.setSiret(siret);
            restaurateurService.createRestaurateur(restaurateur, user);

            return "redirect:/Mon-compte-pro";
        }
        return "redirect:/";
    }

    // displays the professional account page, loads user and restaurant info, and redirects if no pro account exists
    @RequestMapping(value = "/Mon-compte-pro", method = RequestMethod.GET)
    public String accueilPro(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            if(user.getRestaurateur() == null){
                return "redirect:/Creation-compte-pro";
            }
            Restaurant restaurant = user.getRestaurateur().getRestaurant();
            if(restaurant != null) {
                m.addAttribute("restaurant", restaurant);
            }

            m.addAttribute("user", user);
            Map<String, Integer> statTable = reservationRestaurantService.statDeReservation(user.getRestaurateur().getRestaurant().getId());
            m.addAttribute("statReservation", statTable);
        }

        m.addAttribute("content", "accueilPro");
        return "base";
    }

    // displays the restaurant edit page, loads authenticated user and restaurant info
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/edit-restaurant", method = RequestMethod.GET)
    public String configRestaurant(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
            m.addAttribute("restaurant", user.getRestaurateur().getRestaurant());
        }
        m.addAttribute("content", "editRestaurant");
        return "base";
    }

    // updates the restaurant's details and redirects to the restaurant edit page
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/edit-restaurant", method = RequestMethod.POST)
    public String configRestaurantPost(@RequestParam("name") String name,
                                       @RequestParam("address") String address,
                                       @RequestParam("city") String city,
                                       @RequestParam("latitude") float latitude,
                                       @RequestParam("longitude") float longitude) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            restaurantService.saveRestaurantEdit(user.getRestaurateur().getRestaurant(), name, address, city, latitude, longitude);
        }
        return "redirect:/edit-restaurant";
    }

    // updates table information for the restaurant and redirects to the restaurant edit page
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/edit-table", method = RequestMethod.POST)
    public String editTableRestaurantPost(@RequestParam("id") Long id,
                                       @RequestParam("nbPersonne") int nbPersonne,
                                       @RequestParam("nbTable") int nbTable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            TableRestaurant table = tableRestaurantRepository.getById(id);

            table.setNombreTables(nbTable);
            table.setNombrePersonnesMax(nbPersonne);
            tableRestaurantRepository.save(table);

        }
        return "redirect:/edit-restaurant";
    }

    // deletes a table from the restaurant and redirects to the restaurant edit page
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/delete-table", method = RequestMethod.POST)
    public String deleteTableRestaurantPost(@RequestParam("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Restaurant restaurant = user.getRestaurateur().getRestaurant();
            List<TableRestaurant> tables = restaurant.getTables();
            TableRestaurant tableDelete = null;
            for (TableRestaurant table : tables) {
                if (table.getId() == id) {
                    tableDelete = table;
                }
            }
            if (tableDelete != null) {
                restaurant.getTables().remove(tableDelete);
                restaurantRepository.save(restaurant);
                tableRestaurantRepository.delete(tableDelete);
            }
        }
        return "redirect:/edit-restaurant";
    }

    // adds a new table to the restaurant and redirects to the restaurant edit page
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/add-table", method = RequestMethod.POST)
    public String addTableRestaurantPost(@RequestParam("nbPersonne") int nbPersonne,
                                       @RequestParam("nbTable") int nbTable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            restaurateurService.creerTable(user, nbPersonne, nbTable);

        }
        return "redirect:/edit-restaurant";
    }

    // updates the restaurant's schedule for each day of the week and redirects to the restaurant edit page
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/saveHoraire", method = RequestMethod.POST)
    public String saveHoraireRestaurantPost(
            // Lundi
            @RequestParam(value = "lundi_open", required = false) String lundiOpen,
            @RequestParam(value = "lundi_service1_start_hour", required = false) String lundiService1StartHour,
            @RequestParam(value = "lundi_service1_start_minute", required = false) String lundiService1StartMinute,
            @RequestParam(value = "lundi_service1_end_hour", required = false) String lundiService1EndHour,
            @RequestParam(value = "lundi_service1_end_minute", required = false) String lundiService1EndMinute,
            @RequestParam(value = "lundi_service2_start_hour", required = false) String lundiService2StartHour,
            @RequestParam(value = "lundi_service2_start_minute", required = false) String lundiService2StartMinute,
            @RequestParam(value = "lundi_service2_end_hour", required = false) String lundiService2EndHour,
            @RequestParam(value = "lundi_service2_end_minute", required = false) String lundiService2EndMinute,

            // Mardi
            @RequestParam(value = "mardi_open", required = false) String mardiOpen,
            @RequestParam(value = "mardi_service1_start_hour", required = false) String mardiService1StartHour,
            @RequestParam(value = "mardi_service1_start_minute", required = false) String mardiService1StartMinute,
            @RequestParam(value = "mardi_service1_end_hour", required = false) String mardiService1EndHour,
            @RequestParam(value = "mardi_service1_end_minute", required = false) String mardiService1EndMinute,
            @RequestParam(value = "mardi_service2_start_hour", required = false) String mardiService2StartHour,
            @RequestParam(value = "mardi_service2_start_minute", required = false) String mardiService2StartMinute,
            @RequestParam(value = "mardi_service2_end_hour", required = false) String mardiService2EndHour,
            @RequestParam(value = "mardi_service2_end_minute", required = false) String mardiService2EndMinute,

            // Mercredi
            @RequestParam(value = "mercredi_open", required = false) String mercrediOpen,
            @RequestParam(value = "mercredi_service1_start_hour", required = false) String mercrediService1StartHour,
            @RequestParam(value = "mercredi_service1_start_minute", required = false) String mercrediService1StartMinute,
            @RequestParam(value = "mercredi_service1_end_hour", required = false) String mercrediService1EndHour,
            @RequestParam(value = "mercredi_service1_end_minute", required = false) String mercrediService1EndMinute,
            @RequestParam(value = "mercredi_service2_start_hour", required = false) String mercrediService2StartHour,
            @RequestParam(value = "mercredi_service2_start_minute", required = false) String mercrediService2StartMinute,
            @RequestParam(value = "mercredi_service2_end_hour", required = false) String mercrediService2EndHour,
            @RequestParam(value = "mercredi_service2_end_minute", required = false) String mercrediService2EndMinute,

            // Jeudi
            @RequestParam(value = "jeudi_open", required = false) String jeudiOpen,
            @RequestParam(value = "jeudi_service1_start_hour", required = false) String jeudiService1StartHour,
            @RequestParam(value = "jeudi_service1_start_minute", required = false) String jeudiService1StartMinute,
            @RequestParam(value = "jeudi_service1_end_hour", required = false) String jeudiService1EndHour,
            @RequestParam(value = "jeudi_service1_end_minute", required = false) String jeudiService1EndMinute,
            @RequestParam(value = "jeudi_service2_start_hour", required = false) String jeudiService2StartHour,
            @RequestParam(value = "jeudi_service2_start_minute", required = false) String jeudiService2StartMinute,
            @RequestParam(value = "jeudi_service2_end_hour", required = false) String jeudiService2EndHour,
            @RequestParam(value = "jeudi_service2_end_minute", required = false) String jeudiService2EndMinute,

            // Vendredi
            @RequestParam(value = "vendredi_open", required = false) String vendrediOpen,
            @RequestParam(value = "vendredi_service1_start_hour", required = false) String vendrediService1StartHour,
            @RequestParam(value = "vendredi_service1_start_minute", required = false) String vendrediService1StartMinute,
            @RequestParam(value = "vendredi_service1_end_hour", required = false) String vendrediService1EndHour,
            @RequestParam(value = "vendredi_service1_end_minute", required = false) String vendrediService1EndMinute,
            @RequestParam(value = "vendredi_service2_start_hour", required = false) String vendrediService2StartHour,
            @RequestParam(value = "vendredi_service2_start_minute", required = false) String vendrediService2StartMinute,
            @RequestParam(value = "vendredi_service2_end_hour", required = false) String vendrediService2EndHour,
            @RequestParam(value = "vendredi_service2_end_minute", required = false) String vendrediService2EndMinute,

            // Samedi
            @RequestParam(value = "samedi_open", required = false) String samediOpen,
            @RequestParam(value = "samedi_service1_start_hour", required = false) String samediService1StartHour,
            @RequestParam(value = "samedi_service1_start_minute", required = false) String samediService1StartMinute,
            @RequestParam(value = "samedi_service1_end_hour", required = false) String samediService1EndHour,
            @RequestParam(value = "samedi_service1_end_minute", required = false) String samediService1EndMinute,
            @RequestParam(value = "samedi_service2_start_hour", required = false) String samediService2StartHour,
            @RequestParam(value = "samedi_service2_start_minute", required = false) String samediService2StartMinute,
            @RequestParam(value = "samedi_service2_end_hour", required = false) String samediService2EndHour,
            @RequestParam(value = "samedi_service2_end_minute", required = false) String samediService2EndMinute,

            // Dimanche
            @RequestParam(value = "dimanche_open", required = false) String dimancheOpen,
            @RequestParam(value = "dimanche_service1_start_hour", required = false) String dimancheService1StartHour,
            @RequestParam(value = "dimanche_service1_start_minute", required = false) String dimancheService1StartMinute,
            @RequestParam(value = "dimanche_service1_end_hour", required = false) String dimancheService1EndHour,
            @RequestParam(value = "dimanche_service1_end_minute", required = false) String dimancheService1EndMinute,
            @RequestParam(value = "dimanche_service2_start_hour", required = false) String dimancheService2StartHour,
            @RequestParam(value = "dimanche_service2_start_minute", required = false) String dimancheService2StartMinute,
            @RequestParam(value = "dimanche_service2_end_hour", required = false) String dimancheService2EndHour,
            @RequestParam(value = "dimanche_service2_end_minute", required = false) String dimancheService2EndMinute

    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Horaire horaire = user.getRestaurateur().getRestaurant().getHoraire();
            restaurantService.saveHoraireRestaurant(
                    horaire,
                    lundiOpen, lundiService1StartHour, lundiService1StartMinute, lundiService1EndHour, lundiService1EndMinute, lundiService2StartHour, lundiService2StartMinute, lundiService2EndHour, lundiService2EndMinute,
                    mardiOpen, mardiService1StartHour, mardiService1StartMinute, mardiService1EndHour, mardiService1EndMinute, mardiService2StartHour, mardiService2StartMinute, mardiService2EndHour, mardiService2EndMinute,
                    mercrediOpen, mercrediService1StartHour, mercrediService1StartMinute, mercrediService1EndHour, mercrediService1EndMinute, mercrediService2StartHour, mercrediService2StartMinute, mercrediService2EndHour, mercrediService2EndMinute,
                    jeudiOpen, jeudiService1StartHour, jeudiService1StartMinute, jeudiService1EndHour, jeudiService1EndMinute, jeudiService2StartHour, jeudiService2StartMinute, jeudiService2EndHour, jeudiService2EndMinute,
                    vendrediOpen, vendrediService1StartHour, vendrediService1StartMinute, vendrediService1EndHour, vendrediService1EndMinute, vendrediService2StartHour, vendrediService2StartMinute, vendrediService2EndHour, vendrediService2EndMinute,
                    samediOpen, samediService1StartHour, samediService1StartMinute, samediService1EndHour, samediService1EndMinute, samediService2StartHour, samediService2StartMinute, samediService2EndHour, samediService2EndMinute,
                    dimancheOpen, dimancheService1StartHour, dimancheService1StartMinute, dimancheService1EndHour, dimancheService1EndMinute, dimancheService2StartHour, dimancheService2StartMinute, dimancheService2EndHour, dimancheService2EndMinute
            );
        }
        return "redirect:/edit-restaurant";
    }

}
