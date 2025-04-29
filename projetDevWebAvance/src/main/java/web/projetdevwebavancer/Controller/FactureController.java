package web.projetdevwebavancer.Controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.FactureService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FactureController {

//    @Autowired
//    FactureService factureService;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    FactureService factureService;

// loads the authenticated user's invoices and info
@RequestMapping(value = "/facture", method = RequestMethod.GET)
public String pageFacture(Model m) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        m.addAttribute("user", user);
        m.addAttribute("factures",user.getFactures());
        System.out.println(user.getFactures());
        System.out.println(user.getId());
    }


//    factureService.nombreFacture(1);
//    factureService.PrixFacture(1);
//    factureService.NomRestaurantFacture(1);
//    factureService.NomUserFacture(1);


    m.addAttribute("content", "facturationPage");
    return "base";
}

}
