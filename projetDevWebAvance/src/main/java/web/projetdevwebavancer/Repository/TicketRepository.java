package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.Ticket;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findById(long id);
}
