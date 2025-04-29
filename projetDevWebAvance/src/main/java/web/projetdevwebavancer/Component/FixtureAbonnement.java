package web.projetdevwebavancer.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.projetdevwebavancer.Entity.Abonnement;
import web.projetdevwebavancer.Repository.AbonnementRepository;

@Component
public class FixtureAbonnement implements CommandLineRunner {
    @Autowired
    private AbonnementRepository abonnementRepository;

    @Override
    public void run(String... args) throws Exception {
        if (abonnementRepository.count() == 0) {
            abonnementRepository.deleteAll();
            Abonnement abonnement1 = new Abonnement();
            Abonnement abonnement2 = new Abonnement();
            Abonnement abonnement3 = new Abonnement();
            abonnement1.setNom("Classique");
            abonnement1.setPrix(1999);
            abonnement2.setNom("Pro");
            abonnement2.setPrix(2999);
            abonnement3.setNom("Grande Entreprise");
            abonnement3.setPrix(3999);
            abonnementRepository.save(abonnement1);
            abonnementRepository.save(abonnement2);
            abonnementRepository.save(abonnement3);
        }
    }
}
