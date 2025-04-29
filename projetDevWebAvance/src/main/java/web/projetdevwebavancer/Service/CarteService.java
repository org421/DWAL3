package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.projetdevwebavancer.Entity.Carte;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.CarteRepository;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.UserRepository;

@Service
public class CarteService {

    @Autowired
    private CarteRepository carteRepository;

    public Carte getCarte() {
        return carteRepository.findAll().stream().findFirst().orElse(null);
    }

    public Carte saveCarte (Carte carte) {
        return carteRepository.save(carte);
    }

    public Carte getCarteById(Long id) {
        return carteRepository.findById(id).orElse(null);
    }

}
