package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.projetdevwebavancer.Entity.Carte;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.CarteRepository;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.UserRepository;

@Controller
public class RestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private CarteRepository carteRepository;


    // loads restaurant details, its menu, and user info, and returns the "base" view with "restaurant" content
    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET)
    public String register(Model m, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

        Restaurant restaurant = restaurantRepository.findById(id).get();
        if(restaurant.getRestaurateur().getAbonnementActive() == null){
            return "redirect:/";
        }
        Carte carte = carteRepository.findByRestaurant(restaurant);
        m.addAttribute("restaurant", restaurant);
        m.addAttribute("content", "restaurant");
        m.addAttribute("carte", carte);
        System.out.println(carte);
        return "base";
    }
}
