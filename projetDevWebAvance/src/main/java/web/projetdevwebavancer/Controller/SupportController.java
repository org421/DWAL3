package web.projetdevwebavancer.Controller;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.Message;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.Ticket;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.MessageRepository;
import web.projetdevwebavancer.Repository.TicketRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.TicketService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Controller
public class SupportController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    TicketService ticketService;


    // displays the user's support tickets and returns the "base" view with "voirTicket" content
    @RequestMapping(value = "/mes_ticket", method = RequestMethod.GET)
    public String pageMesTicket(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
            m.addAttribute("tickets",user.getTickets());
        }

        m.addAttribute("content", "voirTicket");
        return "base";
    }

    // displays the ticket creation page and loads authenticated user info
    @RequestMapping(value = "/creationTicket", method = RequestMethod.GET)
    public String pageTicket(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
         //   m.addAttribute("ticket",user.getTickets());
        }
        m.addAttribute("content", "creationTicket");
        return "base";
    }

    // creates a new support ticket for the authenticated user and redirects to the ticket creation page
    @RequestMapping(value = "/creationTicket", method = RequestMethod.POST)
    public String configRestaurantPost(@RequestParam("titre") String titre,
                                       @RequestParam("description") String description
                                       ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Ticket ticket = new Ticket();
            ticket.setDescription(description);
            ticket.setTitre(titre);
            ticket.setStatus(ticket.OUVERT); // 0 mean its open
            ticket.setPrioriter(ticket.FAIBLE);
            ticket.setDateCreation(LocalDateTime.now());
            ticket.setDateModification(LocalDateTime.now());
            ticket.setIdClient(user);
            user.getTickets().add(ticket);
            ticketRepository.save(ticket);
            userRepository.save(user);

        }
        return "redirect:/creationTicket";
    }




//    @RequestMapping(value = "/chatTicket", method = RequestMethod.GET)
//    public String pageMesChatTicket(Model m) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String email = authentication.getName();
//            User user = userRepository.findByEmail(email);
//            m.addAttribute("user", user);
//            m.addAttribute("tickets",user.getTickets());
//        }
//
//        m.addAttribute("content", "chatTicket");
//        return "base";
//    }

    // displays the chat page for a specific support ticket and returns the "base" view with "chatTicket" content
    @RequestMapping(value = "/chatTicket/{id}", method = RequestMethod.GET)
    public String chat(Model m, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
            Ticket ticket = ticketRepository.findById(id).get();
            m.addAttribute("messages",ticket.getChat());
            m.addAttribute("id",id);
//            m.addAttribute("messages",ticketService.messages(id));
            System.out.println(ticket.getChat());
            //System.out.println("abc");
            if((user != ticket.getIdClient()) && !user.getRole().equals("ROLE_ADMIN")) {
                return "redirect:/";
            }

        }
        m.addAttribute("content", "chatTicket");
        return "base";
    }

    // sends a new message to the chat of a specific support ticket and redirects to the chat page
    @RequestMapping(value = "/chatTicket/{id}", method = RequestMethod.POST)
    public String chatEnvoie(@RequestParam("chat") String chat,@PathVariable("id") Long id
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Ticket ticket = ticketRepository.findById(id).get();
            Message message = new Message();
            message.setIdClient(user);
            message.setDateEnvoie(LocalDateTime.now());
            message.setMessage(chat);
            ticket.getChat().add(message);
            messageRepository.save(message);
            ticketRepository.save(ticket);
            System.out.println(message);
        }
        return "redirect:/chatTicket/{id}";
    }



}
