package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.Categorie;
import web.projetdevwebavancer.Entity.Menu;
import web.projetdevwebavancer.Entity.Plat;
import web.projetdevwebavancer.Repository.CategorieRepository;
import web.projetdevwebavancer.Repository.MenuRepository;
import web.projetdevwebavancer.Repository.PlatRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PlatService {

    @Autowired
    private PlatRepository platRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private MenuRepository menuRepository;

    public Optional<Plat> getPlatById(Long id) {
        return platRepository.findById(id);
    }

    // creates a new dish (Plat) with a name, description, price, images (icon and main image), associates it with a category, and saves it
    public Plat createPlat(String nom, String description, Integer prix, MultipartFile icone, MultipartFile image, Categorie categorie) throws IOException {
        Plat plat = new Plat();
        plat.setNom(nom);
        plat.setDescription(description);
        plat.setPrix(prix);
        categorie.getPlats().add(plat);

        if (!icone.isEmpty()) {
            String iconeBase64 = "data:" + icone.getContentType() + ";base64," + Base64.getEncoder().encodeToString(icone.getBytes());
            plat.setIcone(iconeBase64);
        }

        if (!image.isEmpty()) {
            String imageBase64 = "data:" + image.getContentType() + ";base64," + Base64.getEncoder().encodeToString(image.getBytes());
            plat.setImage(imageBase64);
        }

        platRepository.save(plat);
        categorieRepository.save(categorie);
        return plat;
    }

    // deletes a dish (Plat) from the database by its ID
    public void deletePlatById(Long id, Long idc) {
        Plat plat = platRepository.getById(id);
        Categorie categorie = categorieRepository.getById(idc);
        List<Menu> menus = menuRepository.findByCategorieId(idc);
        for (Menu menu : menus) {
            menu.getPlats().remove(plat);
            menuRepository.save(menu);
        }
        categorie.getPlats().remove(plat);
        categorieRepository.save(categorie);

        platRepository.deleteById(id);
    }

    // updates an existing dish (Plat) by its ID, modifies its name, description, price, images, and category association, then saves the changes
    public Plat updatePlat(Long id, String nom, String description, Integer prix, MultipartFile icone, MultipartFile image, Categorie categorie) throws IOException {

        Plat plat = platRepository.findById(id).orElseThrow(null);

        plat.setNom(nom);
        plat.setDescription(description);
        plat.setPrix(prix);
        categorie.getPlats().add(plat);

        if (icone != null && !icone.isEmpty()) {
            String iconeBase64 = "data:" + icone.getContentType() + ";base64," + Base64.getEncoder().encodeToString(icone.getBytes());
            plat.setIcone(iconeBase64);
        }

        if (image != null && !image.isEmpty()) {
            String imageBase64 = "data:" + image.getContentType() + ";base64," + Base64.getEncoder().encodeToString(image.getBytes());
            plat.setImage(imageBase64);
        }

        platRepository.save(plat);
        categorieRepository.save(categorie);
        return plat;
    }

    // retrieves the list of dishes (Plats) for a specific category by its ID
    public List<Plat> getPlatsByCategorieId(Long categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(null);
        return categorie.getPlats();
    }

    // retrieves a list of dishes (Plats) by a list of dish IDs
    public List<Plat> getPlatsByIds(List<Long> ids) {
        return platRepository.findAllById(ids);
    }


}
