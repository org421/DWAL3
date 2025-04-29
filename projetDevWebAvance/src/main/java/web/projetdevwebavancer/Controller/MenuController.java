package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Menu;
import web.projetdevwebavancer.Entity.Plat;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.CategorieRepository;
import web.projetdevwebavancer.Repository.MenuRepository;
import web.projetdevwebavancer.Repository.PlatRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.CategorieService;
import web.projetdevwebavancer.Service.MenuService;
import web.projetdevwebavancer.Service.PlatService;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlatService platService;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private PlatRepository platRepository;
    @Autowired
    private MenuRepository menuRepository;

    // loads menus for the category and user info
    @RequestMapping(value = "/edit-carte/categories/{id}", method = RequestMethod.GET)
    public String getAllMenus(@PathVariable("id") Long id ,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
        List<Menu> menus = menuRepository.findByCategorieId(id);
        model.addAttribute("idc", id);
        model.addAttribute("menus", menus);
        model.addAttribute("content", "menu");

        return "base";
    }

    // creates a new menu for the specified category and redirects to the category's menus page
    @PostMapping("/edit-carte/add-menus/{id}")
    public String createMenu(@PathVariable("id") Long categorieId, @RequestParam("nom") String menuName, @RequestParam("prix") Double prixMenu, @RequestParam("description") String description, RedirectAttributes redirectAttributes) {
        System.out.println(" creation d'un pour menu pour la categorieId: " + categorieId);
        Categorie categorie = categorieService.getCategorieById(categorieId);
        Menu menu = new Menu();
        menu.setCategorie(categorie);
        menu.setNom(menuName);
        menu.setPrix(prixMenu);
        menu.setDescription(description);
        categorie.getMenues().add(menu);
        menuRepository.save(menu);
        categorieRepository.save(categorie);
        redirectAttributes.addFlashAttribute("successMessage","Menu créé avec succès !");

        return "redirect:/edit-carte/categories/" + categorieId;
    }

    // loads the menu details and its related dishes
    @GetMapping("/edit-carte/menus/{id}/details")
    public String getMenuDetails(@PathVariable Long id, Model model) {
        Menu menu = menuService.getMenuById(id).orElseThrow(() -> new RuntimeException("Menu introuvable."));
        List<Plat> plats = platService.getPlatsByCategorieId(menu.getCategorie().getId());
        System.out.println("PLATS:" + plats);
        model.addAttribute("menu", menu);
        model.addAttribute("plats", plats);

        return "menu-details";
    }

    // loads menu and dishes info
    @GetMapping("/edit-carte/categories/{idc}/menus/{id}/plats")
    public String viewPlatsForMenu(@PathVariable Long idc,@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }
        Menu menu = menuService.getMenuById(id).orElseThrow(null);
        System.out.println("menu:" + menu);
        List<Plat> plats = platService.getPlatsByCategorieId(idc);
        System.out.println("PLATS id:" + idc  + plats);
        model.addAttribute("menu", menu);
        model.addAttribute("plats", plats);
        model.addAttribute("platsCheck", menu.getPlats());
        model.addAttribute("content", "menu-plats");
        return "base";
    }


    // updates the list of dishes for a menu
    @PostMapping("/edit-carte/categories/{idc}/menus/{id}/update-plats")
    public String updateMenuPlats(RedirectAttributes r,@PathVariable Long idc, @PathVariable Long id, @RequestParam(required = false) List<Long> platsIds) {

        Menu menu = menuService.getMenuById(id).orElseThrow(null);

        List<Plat> allPlats = menu.getPlats();
        menu.getPlats().clear();

        for(Long platId : platsIds){
            Plat newPlat = platRepository.findById(platId).orElseThrow();
            menu.getPlats().add(newPlat);
            menuRepository.save(menu);
        }
        r.addFlashAttribute("successMessagePlat", "Les plats pour ce menu ont été mis à jour !");
        return "redirect:/edit-carte/categories/" + idc + "/menus/" + id + "/plats";
    }

    // emoves all dishes from a menu
    @PostMapping("/edit-carte/categories/{idc}/menus/{id}/clear-plats")
    public String clearPlatsFromMenu(@PathVariable Long idc, @PathVariable Long id, RedirectAttributes r) {
        Menu menu = menuService.getMenuById(id).orElseThrow(null);
        menu.getPlats().clear();
        menuRepository.save(menu);
        r.addFlashAttribute("successMessagePlatVide", "Les plats pour ce menu ont été vidés !");
        return "redirect:/edit-carte/categories/" + idc + "/menus/" + id + "/plats";
    }

    // deletes the specified menu
    @RequestMapping(value = "/suppr-menu", method = RequestMethod.POST)
    public String deleteMenu(@RequestParam("idMenu") Long id) {


        long idc = menuService.deleteMenu(id);
        return "redirect:/edit-carte/categories/" + idc ;
    }





}
