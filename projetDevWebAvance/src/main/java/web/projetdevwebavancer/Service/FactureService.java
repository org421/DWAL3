package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Facture;
import web.projetdevwebavancer.Entity.Plat;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.FactureRepository;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.UserRepository;

import java.util.List;

@Service
public class FactureService {

    @Autowired
    FactureRepository factureRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

//public int prixFacture(Facture facture) {
//    int prixFacture = 0;
//    List<Plat> platAcheter= facture.getPlatsAchat();
//    for (Plat plat : platAcheter) {
//        prixFacture = prixFacture + plat.getPrix();
//    }
//    System.out.println(prixFacture);
//    return prixFacture;
//}

//    public int nombreFacture(int idClient) {
//        int nombreFacture = 0;
//        List<Facture> listeFacture = factureRepository.findByidClient(idClient);
//        for (Facture facture : listeFacture) {
//            nombreFacture = nombreFacture + 1;
//        }
//        System.out.println(nombreFacture);
//        return nombreFacture;
//    }
//
//    public int PrixFacture(int idClient) {
//        int prixFacture = 0;
//        List<Facture> listeFacture = factureRepository.findByidClient(idClient);
//        for (Facture facture : listeFacture) {
//            List<Plat> plat = facture.getPlatsAchat();
//            for (Plat plat1 : plat) {
//                prixFacture = prixFacture + plat1.getPrix();
//            }
//        }
//        System.out.println(prixFacture);
//        return prixFacture;
//    }
//
//    public String NomRestaurantFacture(int idRestaurant) {
//        Restaurant NomRestaurant = restaurantRepository.findByid(idRestaurant);
//        String nomRestaurant = NomRestaurant.getName();
//        System.out.println(nomRestaurant);
//        return nomRestaurant;
//    }
//
//    public String NomUserFacture(int idUser) {
//        User user = userRepository.findById(idUser);
//        String userprenom;
//        String usernom;
//
//        String nomPrenom;
//        usernom = user.getNom();
//        userprenom=user.getPrenom();
//        nomPrenom=(usernom+" "+userprenom);
//        System.out.println(nomPrenom);
//        return nomPrenom;
//    }








}
