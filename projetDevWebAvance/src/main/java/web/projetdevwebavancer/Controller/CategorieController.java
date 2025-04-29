package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.projetdevwebavancer.Entity.Carte;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.CarteRepository;
import web.projetdevwebavancer.Repository.CategorieRepository;
import web.projetdevwebavancer.Repository.PlatRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.CategorieService;
import web.projetdevwebavancer.Service.MenuService;
import web.projetdevwebavancer.Service.PlatService;
import web.projetdevwebavancer.Service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuService menuService;
    @Autowired
    private PlatService platService;
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private PlatRepository platRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    // loads authenticated user and categories
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/edit-carte/categories", method = RequestMethod.GET)
    public String editCategories(Model model) {
        List<Categorie> categories = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
            categories = user.getRestaurateur().getRestaurant().getCarte().getCategories();
        }

        model.addAttribute("categories", categories);
        model.addAttribute("content", "categorie");
        return "base";
    }

    // adds a new category to the user's restaurant "carte"
    @Secured("ROLE_RESTAURATEUR")
    @PostMapping("/edit-carte/add-categories")
    public String addCategorie(@RequestParam("nom") String name,RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            Categorie categorie = new Categorie();
            categorie.setEnabled(true );
            categorie.setNom(name);
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            Carte carte = carteRepository.findByRestaurant(user.getRestaurateur().getRestaurant());
            categorie.setCarte(carte);
            carte.getCategories().add(categorie);
            categorieService.saveCategorie(categorie);
            carteRepository.save(carte);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Catégorie ajoutée avec succès !");
        return "redirect:/edit-carte/categories";
    }

    // oads the category's dishes and user info
    @Secured("ROLE_RESTAURATEUR")
    @GetMapping("/edit-carte/categories/{id}/plats")
    public String viewPlatsByCategory(@PathVariable("id") Long id, Model m, RedirectAttributes r) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

        Categorie categorie = categorieService.getCategorieById(id);
        r.addFlashAttribute("successMessage", "Catégorie ajoutée avec succès !");
        m.addAttribute("categorie", categorie);
        m.addAttribute("idc", id);
        m.addAttribute("plats", categorie.getPlats());

        m.addAttribute("content", "plat");
        return "base";
    }

    // deletes a category
    @Secured("ROLE_RESTAURATEUR")
    @RequestMapping(value = "/suppr-categorie", method = RequestMethod.POST)
    public String deleteCategorie(@RequestParam("idCategorie") Long id, RedirectAttributes r) {


        long idCarte = categorieService.deleteCategorie(id);
        System.out.println("idcarte" + idCarte);
        r.addFlashAttribute("successMessageSuppr","Catégorie supprimée avec succès !");
        return "redirect:/edit-carte/categories" ;
    }
}