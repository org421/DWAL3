package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.*;
import web.projetdevwebavancer.Repository.AbonnementActiveRepository;
import web.projetdevwebavancer.Repository.AbonnementRepository;
import web.projetdevwebavancer.Repository.RestaurateurRepository;

import java.time.LocalDate;

@Service
public class AbonnementService {
    @Autowired
    AbonnementRepository abonnementRepository;
    @Autowired
    AbonnementActiveRepository abonnementActiveRepository;
    @Autowired
    private RestaurateurRepository restaurateurRepository;

    // Adds or updates an active subscription for a restaurateur, applying a promo code if provided, and sets the end date to one month from the start date
    public void addAbonnementUser(Abonnement abonnement, Restaurateur restaurateur, CodePromo codePromo, Boolean renouvellement, int moyenPaiement) {
        AbonnementActive abonnementActive;

        if(restaurateur.getAbonnementActive() == null) {
            System.out.println("create new abonnement active");
            abonnementActive = new AbonnementActive();
        } else {
            System.out.println("update abonnement active");
            abonnementActive = abonnementActiveRepository.findByRestaurateur(restaurateur);
        }

        abonnementActive.setAbonnement(abonnement);
        abonnementActive.setCodePromo(codePromo);
        abonnementActive.setDateFin(LocalDate.now().plusMonths(1)); //The end date of the active subscription is the next month following the purchase of the subscrition
        abonnementActive.setRenouvellement(renouvellement);
        abonnementActive.setMoyenPaiement(moyenPaiement);
        if(codePromo == null) {
            abonnementActive.setPrixPaye(abonnement.getPrix());
        } else {
            abonnementActive.setPrixPaye(abonnement.getPrix() - (int)Math.ceil((abonnement.getPrix() * codePromo.getReduction()))); // price - price * reduction
        }
        abonnementActive.setRestaurateur(restaurateur);
        abonnementActiveRepository.save(abonnementActive);
        restaurateur.setAbonnementActive(abonnementActive);
        restaurateurRepository.save(restaurateur);
    }

    public void deactivateAbonnement(AbonnementActive abonnementActive){
        if(abonnementActive.isRenouvellement()) {
            System.out.println("update abonnement is now deactivated");
            abonnementActive.setRenouvellement(false);
            abonnementActiveRepository.save(abonnementActive);
        } else {
            System.out.println("update abonnement is already deactivated");
        }
    }
}
