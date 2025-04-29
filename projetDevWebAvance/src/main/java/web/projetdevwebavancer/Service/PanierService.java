package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.projetdevwebavancer.Entity.Abonnement;
import web.projetdevwebavancer.Entity.LignePanier;
import web.projetdevwebavancer.Entity.Panier;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.LignePanierRepository;
import web.projetdevwebavancer.Repository.PanierRepository;
import web.projetdevwebavancer.Repository.UserRepository;

import javax.swing.text.html.parser.Entity;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PanierService {
    @Autowired
    PanierRepository panierRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LignePanierService lignePanierService;
    @Autowired
    private LignePanierRepository lignePanierRepository;

    // creates a new cart for a user, sets it as a subscription cart if specified, initializes its status and total price, and saves it
    public void createPanier(User user, boolean abonnement) {
        Panier panier = new Panier();
        panier.setUser(user);
        panier.setAbonnement(abonnement);
        panier.setStatut(1);
        panier.setPrixTotal(0);

        panierRepository.save(panier);

        user.setPanier(panier);
        userRepository.save(user);
    }

    // adds a subscription to a user's cart, creates a new cart line for the subscription, sets the cart's total price, and saves it
    public void addAbonnementPanier(Panier panier, Abonnement abonnement) {
        try {
            System.out.println("idAbonnement : " + abonnement.getId());
            lignePanierService.createLignePanier(panier, abonnement, null, null);
            panier.setPrixTotal(abonnement.getPrix());
            panierRepository.save(panier);
        } catch (Exception ignored) {
            System.out.println("Erreur lors de l'ajout de l'abonnement dans le panier !");
        }
    }

    // removes a specific cart line from a user's cart, updates the total price, and saves the updated cart
    public void removePanier(Panier panier, LignePanier lignePanier) {
        try {
            int prix = 0;

            if(lignePanier.getAbonnement() != null) {
                prix = lignePanier.getAbonnement().getPrix();
            }
            if(lignePanier.getPlat() != null) {
                prix = lignePanier.getPlat().getPrix();
            }
            if(lignePanier.getMenu() != null) {
                prix = BigDecimal.valueOf(lignePanier.getMenu().getPrix()).intValue() * 100;
            }
            panier.setPrixTotal(panier.getPrixTotal() - prix);
            panier.getLignePanier().remove(lignePanier);
            panierRepository.save(panier);

        } catch (Exception ignored) {
            System.out.println("Erreur lors de la suppression d'une ligne du panier !");
        }
    }

    // clears all items from a user's cart, deletes all cart lines from the database, resets the cart total to zero, and saves the changes
    @Transactional
    public void emptyPanier(Panier panier){
        try{
            List<LignePanier> lignePaniers = panier.getLignePanier();
            System.out.println("Suppression de "+lignePaniers.size()+" lignes");
            lignePanierRepository.deleteAll(lignePaniers);
            panier.getLignePanier().clear(); // Clear the list in the memory
            panier.setPrixTotal(0);
            panierRepository.save(panier);

        } catch (Exception ignored) {
            System.out.println("Panier vide !");
        }
    }
}
