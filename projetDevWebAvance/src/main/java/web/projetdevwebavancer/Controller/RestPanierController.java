package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import web.projetdevwebavancer.Entity.LignePanier;
import web.projetdevwebavancer.Entity.Menu;
import web.projetdevwebavancer.Entity.Panier;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.*;
import web.projetdevwebavancer.Service.PanierService;

import java.math.BigDecimal;

@RestController
public class RestPanierController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PanierService panierService;

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    private LignePanierRepository lignePanierRepository;
    @Autowired
    private PanierRepository panierRepository;

    // adds a menu to the user's cart, creating or resetting the cart if necessary
    @RequestMapping(value = "/add-panier-menu/{id}", method = RequestMethod.GET)
    public void addMenuPanier(@PathVariable("id") Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(null);
        if(menu != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                User user = userRepository.findByEmail(email);
                Panier panier = user.getPanier();
                if (panier != null) {
                    if (panier.isAbonnement()) {
                        panierService.emptyPanier(panier);
                        panier.setAbonnement(false);
                    }
                } else {
                    panierService.createPanier(user, false);
                    panier = user.getPanier();
                }
                LignePanier lignePanier = new LignePanier();
                int prix = BigDecimal.valueOf(menu.getPrix()).intValue() * 100;
                panier.setPrixTotal(panier.getPrixTotal() + prix);
                lignePanier.setMenu( menu );
                lignePanier.setPanier(panier);
                panier.getLignePanier().add(lignePanier);
                lignePanierRepository.save(lignePanier);
                panierRepository.save(panier);
            }
        }
    }

    // removes a menu from the user's cart and updates the total price
    @RequestMapping(value = "/remove-panier-menu/{id}", method = RequestMethod.GET)
    public void removeMenuPanier(@PathVariable("id") Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(null);
        if(menu != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                User user = userRepository.findByEmail(email);
                Panier panier = user.getPanier();
                if (panier != null) {
                    if (panier.isAbonnement()) {
                        return;
                    } else {
                        for( LignePanier lignePanier : panier.getLignePanier()) {
                            if(lignePanier.getMenu() == menu) {
                                int prix = BigDecimal.valueOf(menu.getPrix()).intValue() * 100;
                                panier.getLignePanier().remove(lignePanier);
                                panier.setPrixTotal(panier.getPrixTotal() - prix);
                                lignePanierRepository.delete(lignePanier);
                                break;
                            }
                        }
                        panierRepository.save(panier);
                    }
                }
            }
        }
        return;
    }
}
