package web.projetdevwebavancer.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.CodePromo;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.CodePromoRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.CodePromoService;
import web.projetdevwebavancer.Service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class CodePromoController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CodePromoService codePromoService;
    @Autowired
    private CodePromoRepository codePromoRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/codePromo", method = RequestMethod.GET)
    public String viewCodePromo(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

        List<CodePromo> codesPromos = codePromoRepository.findAll();


        m.addAttribute("codesPromos", codesPromos);
        m.addAttribute("content", "codePromoAdmin");
        return "adminBase";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/codePromo", method = RequestMethod.POST)
    public String switchCodePromo(@RequestParam("id") Long id) {

        CodePromo codePromo = codePromoRepository.findById(id).get();
        if(codePromo.isActive()){
            codePromo.setActive(false);
        } else {
            if(codePromo.getDateFin().isAfter(LocalDate.now())) { //We can reactivate a code only if its end date is after today
                codePromo.setActive(true);
            }
        }
        codePromoRepository.save(codePromo);

        return "redirect:/admin/codePromo";
    }

    @RequestMapping(value = "/reclamation", method = RequestMethod.POST)
    public String reclaCodePromo(
            @RequestParam("code") String code
    ) {

        CodePromo codePromo = codePromoRepository.findByCode(code);

        if(codePromo == null){
            System.out.println("codePromo not found");
            return "redirect:/paiement";
        }

        codePromo = codePromoRepository.findByCode(code);
        if(!codePromo.isActive()) { //cannot use the code if its not activated
            System.out.println("codePromo is not active");
            return "redirect:/paiement";
        }

        if(codePromo.getNbActuel() >= codePromo.getNbMax()){
            System.out.println("codePromo is over max"); //cannot use the code if it has been activated too much
            return "redirect:/paiement";
        }

        if(codePromo.getDateFin().isBefore(LocalDate.now())) {
            System.out.println("codePromo is expired"); //cannot use the code if it's expired
            return "redirect:/paiement";
        }

        System.out.println("codePromo found");
        return "redirect:/paiement/" + code;
    }

    // loads authenticated user info
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/createCodePromo", method = RequestMethod.GET)
    public String createCodePromo(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }
        m.addAttribute("content", "createCodePromo");
        return "adminBase";
    }

    // validates the date range, creates a new promo code
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/newCodePromo", method = RequestMethod.POST)
    public String newCodePromo(
            Model m,
            @RequestParam("code") String code,
            @RequestParam("nbmax") int nbmax,
            @RequestParam("dateDebut") String dateD,
            @RequestParam("dateFin") String dateF,
            @RequestParam("reduction") String reduction

    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        float reduc;


        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

        if(codePromoRepository.findByCode(code) != null){
            System.out.println("codePromo already exists");
            return "redirect:/admin/createCodePromo";
        }



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Create the begin date et the finish date
        LocalDate dateDebut = LocalDate.parse(dateD, formatter);
        LocalDate dateFin = LocalDate.parse(dateF, formatter);

        if(dateDebut.isAfter(dateFin)) {
            System.out.println("Dates incorrectes"); // à modifier
            return "redirect:/admin/createCodePromo";
        }

        if(dateFin.isBefore(LocalDate.now())) {
            System.out.println("Dates déjà expirée");
            return "redirect:/admin/createCodePromo";
        }

        reduc = (float) Integer.parseInt(reduction) /100;
        codePromoService.saveCodePromo(code, nbmax, dateDebut, dateFin, reduc);

        return "redirect:/admin/codePromo";
    }
}
