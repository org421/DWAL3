package web.projetdevwebavancer.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Message;
import web.projetdevwebavancer.Entity.Ticket;
import web.projetdevwebavancer.Repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    // retrieves a list of messages (chat) for a specific ticket by its ID, handling potential null values using an Optional
    public List<Message> messages (Long id){ //Optional to dont have null pb thanks IntelliJ
        Optional<Ticket> ticket = ticketRepository.findById(id);
        List<Message> messages = new ArrayList<>();
        messages = ticket.get().getChat();
        return messages;
    }


}
