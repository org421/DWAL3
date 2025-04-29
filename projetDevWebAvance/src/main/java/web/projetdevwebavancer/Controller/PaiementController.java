package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.projetdevwebavancer.Entity.Abonnement;
import web.projetdevwebavancer.Entity.CodePromo;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.AbonnementRepository;
import web.projetdevwebavancer.Repository.CodePromoRepository;
import web.projetdevwebavancer.Repository.PanierRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.PaiementService;

import java.time.LocalDate;

@Controller
public class PaiementController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AbonnementRepository abonnementRepository;
    @Autowired
    PaiementService paiementService;
    @Autowired
    private CodePromoRepository codePromoRepository;
    @Autowired
    private PanierRepository panierRepository;

    // loads authenticated user info, and returns the "base" view with "paiement" content
    @RequestMapping(value = {"/paiement", "/paiement/{codePromo}"}, method = RequestMethod.GET)
    public String paiement(@PathVariable(value = "codePromo", required = false) String code ,Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

        CodePromo codePromo;

        if(code != null){
            System.out.println("code : " + code);
            codePromo = codePromoRepository.findByCode(code);
            m.addAttribute("codePromo", codePromo);
        }

        m.addAttribute("content", "paiement");
        return "base";
    }

    // processes subscription payment, optionally in developer mode
    @RequestMapping(value = "/paiementAbonnement", method = RequestMethod.POST)
    public String paiementAbonnement(
            Model m,
            @RequestParam(value = "renouvellement", defaultValue = "off") String renouvellement,
            @RequestParam(value = "codePromo", defaultValue = "") String codePromo,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam("adresse") String adresse,
            @RequestParam("modePaiement") String modePaiement,
            @RequestParam("cc-nom") String ccnom,
            @RequestParam("cc-numero") String ccnumero,
            @RequestParam("cc-expiration") String ccexpiration,
            @RequestParam("cc-cvv") String cccvv,
            @RequestParam(value = "paiementDev", defaultValue = "off") String paiementDev
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String emailAut = authentication.getName();
            User user = userRepository.findByEmail(emailAut);
            m.addAttribute("user", user);
        }
        String emailAut = authentication.getName();
        User user = userRepository.findByEmail(emailAut);
        Boolean reNew;
        int paimentMean;
        int price;

        System.out.println("renouvellement : " + renouvellement);
        System.out.println("codePromo : " + codePromo);
        System.out.println("nom : " + nom);
        System.out.println("prenom : " + prenom);
        System.out.println("email : " + email);
        System.out.println("adresse : " + adresse);
        System.out.println("modePaiement: " + modePaiement);
        System.out.println("cc-nom : " + ccnom);
        System.out.println("cc-numero : " + ccnumero);
        System.out.println("cc-expiration : " + ccexpiration);
        System.out.println("cc-cvv : " + cccvv);
        System.out.println("paiementDev : " + paiementDev);

        //Payment in  developer mode
        if(paiementDev.equals("on")){

            if(codePromo.equals("")) {
                price = user.getPanier().getPrixTotal();
            } else {
                CodePromo codePromo1 = codePromoRepository.findByCode(codePromo);
                codePromo1.setNbActuel(codePromo1.getNbActuel()+1);
                price = (int) Math.ceil(user.getPanier().getPrixTotal() - (user.getPanier().getPrixTotal() * codePromo1.getReduction()));
            }
            System.out.println("prix à payer : " + price);

            reNew = renouvellement.equals("on");
            paimentMean = Integer.parseInt(modePaiement);
            user.getPanier().setPrixTotal(price);
            panierRepository.save(user.getPanier());
            paiementService.ajoutPanierUser(user, null, reNew, paimentMean);

        }
        else{//Payement in normal mode
            System.out.println("Paiement en mode normal");
        }


        return "redirect:/panier";
    }
}
