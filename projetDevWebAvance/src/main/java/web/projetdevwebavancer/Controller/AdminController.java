package web.projetdevwebavancer.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.Ticket;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.TicketRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.UserService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    // shows the admin dashboard
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String dashboard(Model m) throws MessagingException {

        m.addAttribute("content", "accueilAdmin");
        return "adminBase";
    }

    // loads all users, formats them as JSON, and returns the "adminBase" view
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/utilisateur", method = RequestMethod.GET)
    public String utilisateur(Model m) throws MessagingException {
        List<User> users = userRepository.findAll();

        List<Map<String, ? extends Serializable>> usersDto = users.stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "nom", user.getNom(),
                        "email", user.getEmail(),
                        "enabled", user.isEnabled()
                ))
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String usersJson = objectMapper.writeValueAsString(usersDto);
            m.addAttribute("users", usersJson);
        } catch (Exception e) {

        }
        m.addAttribute("content", "utilisateurAdmin");
        return "adminBase";
    }

    // retrieves all tickets for users, formats them, and returns the "adminBase" view
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/support", method = RequestMethod.GET)
    public String support(Model m) throws MessagingException {
        // take all user
        List<User> users = userRepository.findAll();

        List<Map<String, Object>> ticketsDto = users.stream()
            .flatMap(user -> user.getTickets().stream()
                .map(ticket -> {
                    return Map.of(
                        "id", ticket.getId(),
                        "titre", ticket.getTitre(),
                        "description",ticket.getDescription(),
                        "status",ticket.getStatus(),
                        "priorite",ticket.getPrioriter(),
                        "client", (user.getNom() + " " + user.getPrenom()),
                        "dateCreation",ticket.getDateCreation().toString(),
                        "dateModification", (Object) ticket.getDateModification() //object needed thx intelliJ for patch
                    );
                })
            )
            .collect(Collectors.toList());

        m.addAttribute("tickets", ticketsDto);
        m.addAttribute("content", "ticketAdmin");
        return "adminBase";
    }




    // toggles the status of a ticket between "open" and "closed"
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/ticketAdmin", method = RequestMethod.POST)
    public String switchOpenClose(@RequestParam("id") Long id){
        System.out.println("Arrivée ici ou las bas");
        Ticket ticket = ticketRepository.findById(id).get();
        if(ticket.getStatus()==ticket.OUVERT){
            ticket.setStatus(ticket.FERMER);
        } else {
            ticket.setStatus(ticket.OUVERT);
        }
        ticketRepository.save(ticket);
        return "redirect:/admin/support";
    }


    // toggles the "enabled" status of a user
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/utilisateur", method = RequestMethod.POST)
    public String switchEnabeled(@RequestParam("id") Long id){
        System.out.println("Arrivée ici");
        User user = userRepository.findById(id).get();
        if(user.isEnabled()){
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userRepository.save(user);
        return "redirect:/admin/utilisateur";
    }
}
