package web.projetdevwebavancer.Controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.MailService;


@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    // loads the authenticated user's info
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String pageAccueil(Model m) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

        m.addAttribute("content", "accueil");
        return "base";
    }
}
