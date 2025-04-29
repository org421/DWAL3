package web.projetdevwebavancer.Controller;


import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Plat;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.CategorieRepository;
import web.projetdevwebavancer.Repository.UserRepository;
import web.projetdevwebavancer.Service.CategorieService;
import web.projetdevwebavancer.Service.MailService;
import web.projetdevwebavancer.Service.PlatService;

import java.io.IOException;
import java.util.List;

@Controller
public class PlatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlatService platService;

    @Autowired
    private CategorieService categorieService;
    @Autowired
    private CategorieRepository categorieRepository;

    // creates a new dish for the specified category
    @Secured("ROLE_RESTAURATEUR")
    @PostMapping("/edit-carte/categories/{idc}/plats/add-plat")
    public String addPlat(RedirectAttributes r, @PathVariable("idc") Long idc, @RequestParam String nom, @RequestParam String description, @RequestParam Integer prix, @RequestParam MultipartFile icone, @RequestParam MultipartFile image
    ) throws IOException {
        Categorie categorie = categorieService.getCategorieById(idc);

        platService.createPlat(nom, description, prix, icone, image, categorie);
        r.addFlashAttribute("successMessagePlatCrea", "Plat créé avec succès !");
        return "redirect:/edit-carte/categories/" + idc + "/plats";
    }

    // deletes the specified dish from the category
    @Secured("ROLE_RESTAURATEUR")
    @PostMapping("/edit-carte/categories/{idc}/plats/delete/{idp}")
    public String deletePlat(@PathVariable Long idc,  @PathVariable Long idp, RedirectAttributes r) {
        platService.deletePlatById(idp,idc);
        r.addFlashAttribute("successMessagePlatSuppr","Plat supprimé avec succès !");
        return "redirect:/edit-carte/categories/" + idc + "/plats";
    }

    // displays details of a specific dish
    @Secured("ROLE_RESTAURATEUR")
    @GetMapping("/edit-carte/categories/{idc}/plats/{idp}")
    public String getPlatDetails(@PathVariable Long idc, @PathVariable Long idp, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
        }

        Plat plat = platService.getPlatById(idp).orElseThrow(null);
        model.addAttribute("plat", plat);

        model.addAttribute("idc", idc);
        model.addAttribute("idp", idp);
        model.addAttribute("content", "plat-details");

        return "base";
    }

    // loads dish details for editing and returns the "plat-details" view
    @Secured("ROLE_RESTAURATEUR")
    @GetMapping("/plats/edit/{id}")
    public String editPlat(@PathVariable Long id, Model model) {
        Plat plat = platService.getPlatById(id).orElseThrow(() -> new RuntimeException("Plat introuvable."));
        model.addAttribute("plat", plat);
        return "plat-details\"";
    }

    // updates a dish's information
    @Secured("ROLE_RESTAURATEUR")
    @PostMapping("/edit-carte/categories/{idc}/plats/update/{idp}")
    public String updatePlat(RedirectAttributes r,@PathVariable Long idc, @PathVariable Long idp, @RequestParam String nom, @RequestParam String description, @RequestParam Integer prix, @RequestParam(required = false) MultipartFile icone, @RequestParam(required = false) MultipartFile image) throws IOException {

        Categorie categorie = categorieService.getCategorieById(idc);

        platService.updatePlat(idp, nom, description, prix, icone, image, categorie);
        r.addFlashAttribute("successMessagePlatModif", "Plat modifié avec succès !");
        return "redirect:/edit-carte/categories/" + idc + "/plats/" + idp;
    }



}
