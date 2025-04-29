package web.projetdevwebavancer.Service;

import org.hibernate.sql.results.internal.InitializersList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.*;
import web.projetdevwebavancer.Repository.FactureRepository;
import web.projetdevwebavancer.Repository.PanierRepository;
import web.projetdevwebavancer.Repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaiementService {

    @Autowired
    private AbonnementService abonnementService;
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private PanierService panierService;
    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private UserRepository userRepository;

    // processes the user's cart, activating a subscription if the cart contains one, or identifying it as a product cart, and clears the cart afterward.
    public void ajoutPanierUser(User user, CodePromo codePromo, Boolean renouvellement, int moyenPaiement) {
        Abonnement abonnement;

        if(user.getPanier() == null || user.getPanier().getLignePanier().isEmpty()) {
            System.out.println("User's cart is empty");
            return;
        }
        if(user.getPanier().isAbonnement()) {
            System.out.println("User's cart is abonnement");
            if(user.getRestaurateur() == null) {
                System.out.println("User is not a restaurateur");
            } else {
                abonnement = user.getPanier().getLignePanier().get(0).getAbonnement();
                abonnementService.addAbonnementUser(abonnement, user.getRestaurateur(), codePromo, renouvellement, moyenPaiement);
                panierService.emptyPanier(user.getPanier());
            }
        }
        else{
            Restaurant restaurant = null;
            List<Menu> menus = new ArrayList<>();
            for (LignePanier l : user.getPanier().getLignePanier()) {
                if(l.getMenu() != null) {
                    menus.add(l.getMenu());
                    restaurant = l.getMenu().getCategorie().getCarte().getRestaurant();
                }
            }
            Facture facture = new Facture();
            facture.setIdClient(user);
            facture.setDateFacture(LocalDate.now());
            facture.setMenuAchat(menus);
            facture.setIdRestaurant(restaurant);
            facture.setPrixTTC(user.getPanier().getPrixTotal()/100);
            panierService.emptyPanier(user.getPanier());
            factureRepository.save(facture);
            user.getFactures().add(facture);
            userRepository.save(user);
            System.out.println("User's cart is product");
        }
    }
}
