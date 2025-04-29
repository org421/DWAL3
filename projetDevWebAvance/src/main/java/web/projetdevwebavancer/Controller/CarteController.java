package web.projetdevwebavancer.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.projetdevwebavancer.Entity.*;
import web.projetdevwebavancer.Repository.*;
import web.projetdevwebavancer.Service.CarteService;
import web.projetdevwebavancer.Service.RestaurantService;
import web.projetdevwebavancer.Service.RestaurateurService;
import web.projetdevwebavancer.Service.UserService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Controller
public class CarteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarteService carteService;

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    // loads the authenticated user and the "carte"
    @Secured("ROLE_RESTAURATEUR")
    @GetMapping("/edit-carte")
    public String editCarte(Model m) {
        Carte carte = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
            carte = carteRepository.findByRestaurant(user.getRestaurateur().getRestaurant());

        }
        m.addAttribute("carte", carte);
        m.addAttribute("content","editCarte");
        return "base";
    }

    // saves a new "carte" linked to the authenticated user's restaurant
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/add-carte", method = RequestMethod.POST)
    public String addCarte(@ModelAttribute("carte") Carte carte) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            carte.setRestaurant(user.getRestaurateur().getRestaurant());
            user.getRestaurateur().getRestaurant().setCarte(carte);
            carteService.saveCarte(carte);
            restaurantRepository.save(user.getRestaurateur().getRestaurant());
        }

        return "redirect:/edit-carte";
    }
//    @PostMapping("/add-carte")
//    public String addCarte(Carte carte, RedirectAttributes r) {
//
//        carteService.saveCarte(carte);
//        r.addFlashAttribute("message", "Carte ajoutée avec succès !");
//        return "redirect:/edit-carte";
//    }









}
