package web.projetdevwebavancer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.projetdevwebavancer.Entity.Message;
import web.projetdevwebavancer.Entity.Restaurant;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
