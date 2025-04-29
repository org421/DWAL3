package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Carte;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Menu;
import web.projetdevwebavancer.Entity.Plat;
import web.projetdevwebavancer.Repository.CarteRepository;
import web.projetdevwebavancer.Repository.CategorieRepository;
import web.projetdevwebavancer.Repository.MenuRepository;
import web.projetdevwebavancer.Repository.PlatRepository;

import java.util.List;

@Service
public class CategorieService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private PlatRepository platRepository;
    @Autowired
    private MenuService menuService;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }


    // Deletes a category, its associated dishes, and menus, removes it from the related menu, and returns the ID of the associated menu.
    public long deleteCategorie(Long id) {
            Categorie categorie = categorieRepository.findById(id).orElseThrow(null);
            Carte carte = categorie.getCarte();
            List<Plat> plats = categorie.getPlats();
            for (Plat plat : plats) {
                platRepository.delete(plat);
            }

            for (Menu menu : categorie.getMenues()) {
                if (menu != null) {
                    menuService.deleteMenu(menu.getId());
                }

            }

            categorie.getMenues().clear();

            carte.getCategories().remove(categorie);
            carteRepository.save(carte);

            categorieRepository.delete(categorie);
            return carte.getId();
        }
    }
