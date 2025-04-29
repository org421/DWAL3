package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.*;
import web.projetdevwebavancer.Repository.LignePanierRepository;
import web.projetdevwebavancer.Repository.PanierRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LignePanierService {
    @Autowired
    LignePanierRepository lignePanierRepository;

    @Autowired
    private PanierRepository panierRepository;

    // Creates and saves a new cart line (LignePanier) with the specified cart, subscription, dish, and menu, then adds it to the cart
    public void createLignePanier(Panier panier, Abonnement abonnement, Plat plat, Menu menu) {
        LignePanier lignePanier = new LignePanier();
        lignePanier.setPanier(panier);
        lignePanier.setAbonnement(abonnement);
        lignePanier.setPlat(plat);
        lignePanier.setMenu(menu);


        lignePanierRepository.save(lignePanier);

        panier.getLignePanier().add(lignePanier);

    }
}
