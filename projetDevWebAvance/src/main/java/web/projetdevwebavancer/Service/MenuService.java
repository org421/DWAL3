package web.projetdevwebavancer.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Menu;
import web.projetdevwebavancer.Entity.Plat;
import web.projetdevwebavancer.Repository.CategorieRepository;
import web.projetdevwebavancer.Repository.MenuRepository;
import web.projetdevwebavancer.Repository.PlatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private  CategorieRepository categorieRepository;

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private PlatService platService;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    // updates the name and description of a menu by its ID, saving the changes and returning the updated menu
    public Menu updateMenu(Long id, Menu menuDetails) {
        return menuRepository.findById(id).map(menu -> {
            menu.setNom(menuDetails.getNom());
            menu.setDescription(menuDetails.getDescription());
            return menuRepository.save(menu);
        }).orElseThrow(null);
    }

    // updates the list of dishes associated with a menu by its ID, saving the changes and returning the updated menu
    public Menu updateMenuPlats(Long menuId, List<Plat> plats) {
        return menuRepository.findById(menuId).map(menu -> {
            menu.setPlats(plats);
            return menuRepository.save(menu);
        }).orElseThrow(null);
    }

    // retrieves a menu by its ID, throwing an exception if the menu is not found
    public Menu getMenuWithPlats(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(null);
    }

    // retrieves the list of dishes for a given category ID to be used in a menu
    public List<Plat> getPlatsByCategorieForMenu(Long categorieId) {
        return platService.getPlatsByCategorieId(categorieId);
    }

    // lears all dishes from a menu and saves the changes to the database
    public void clearPlatsFromMenu(Menu menu) {
        menu.getPlats().clear();
        menuRepository.save(menu);
    }

    // deletes a menu by its ID, removes its associated dishes, updates the category it belongs to, and returns the category ID
    public long deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(null);

        for (Plat plat : menu.getPlats()) {
            platRepository.delete(plat);
        }

        menu.getPlats().clear();

        Categorie categorie = menu.getCategorie();

        categorie.getMenues().remove(menu);
        categorieRepository.save(categorie);


        menuRepository.delete(menu);
        return categorie.getId();
    }



}
