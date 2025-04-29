package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.projetdevwebavancer.Entity.Abonnement;
import web.projetdevwebavancer.Entity.LignePanier;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.AbonnementRepository;
import web.projetdevwebavancer.Repository.LignePanierRepository;
import web.projetdevwebavancer.Repository.PanierRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.PanierService;

import java.util.List;

@Controller
public class PanierController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PanierRepository panierRepository;

    @Autowired
    PanierService panierService;
    @Autowired
    AbonnementRepository abonnementRepository;
    @Autowired
    private LignePanierRepository lignePanierRepository;

    // loads authenticated user info, and returns the "base" view with "panier" content
    @RequestMapping(value = "/panier", method = RequestMethod.GET)
    public String panier(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

        m.addAttribute("content", "panier");
        return "base";
    }

    // adds a subscription to the user's cart, creating or resetting the cart if necessary
    @RequestMapping(value = "/addPanier/abonnement/{id}", method = RequestMethod.GET)
    public String addAbonnementPanier(Model m, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);

            if(user.getPanier() == null) {
                //creation of a cart if the user does not have one yet
                System.out.println("Création de panier");
                panierService.createPanier(user, true);
            } else {
                if (!user.getPanier().isAbonnement()) {
                    System.out.println("Remplacement du panier");
                    //We replace the cart that contains other products because a cart that contains a subscription behave differently
                    panierService.emptyPanier(user.getPanier());
                    user.getPanier().setPrixTotal(0);
                    user.getPanier().setAbonnement(true);
                } else {
                    if (!user.getPanier().getLignePanier().isEmpty()) {
                        System.out.println("vidage du panier");
                        panierService.emptyPanier(user.getPanier());
                        user.getPanier().setPrixTotal(0);
                    }
                }
            }
            try {
                Abonnement abonnement = abonnementRepository.findById(id).get();
                System.out.println("ajout dans panier");
                panierService.addAbonnementPanier(user.getPanier(), abonnement);
            } catch (Exception ignored) { }


        }

        return "redirect:/panier";
    }

    // removes a line item from the user's cart
    @RequestMapping(value = "/removeLignePanier/{id}", method = RequestMethod.GET)
    public String removeLignePanier(Model m, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);

            if(user.getPanier() != null) {
                LignePanier lignePanier = lignePanierRepository.findById(id).get();
                panierService.removePanier(user.getPanier(), lignePanier);
            }
        }
        return "redirect:/panier";
    }
}
