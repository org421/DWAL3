package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.AbonnementService;
import web.projetdevwebavancer.Service.UserService;

@Controller
public class AbonnementController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private AbonnementService abonnementService;

    // handles GET requests to "/abonnement" and adds user info to the model
    @RequestMapping(value = "/abonnement", method = RequestMethod.GET)
    public String abonnement(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }
        m.addAttribute("content", "abonnement");
        return "base";
    }

    @RequestMapping(value = "/deactivateAbo", method = RequestMethod.GET)
    public String deactivation(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        if(user.getRestaurateur() != null) { //The user must be a restaurateur
            abonnementService.deactivateAbonnement(user.getRestaurateur().getAbonnementActive());
        } else {
            System.out.println("User is not a restaurateur");
        }


        return "redirect:/Mon-compte-pro";
    }
}
