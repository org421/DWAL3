package web.projetdevwebavancer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;



@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // displays the user's profile page with their information
    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public String monProfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("content", "monProfil");
        model.addAttribute("user", user);

        return "base";
    }

    // changes the user's password if the current password is correct and the new passwords match
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword, @RequestParam("confirmNewPassword") String confirmNewPassword, RedirectAttributes redirectAttributes, Principal p) {
        String email = p.getName();
        User user = userService.getUserByEmail(email);
        boolean passwordChanged = userService.changePassword(email, currentPassword, newPassword);

        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("passwordError", "Les nouveaux mots de passe ne correspondent pas.");
            return "redirect:/profil?section=changePassword";
        }

        if (!passwordChanged) {
            redirectAttributes.addFlashAttribute("passwordError", "Le mot de passe actuel est incorrect.");
        } else {
            redirectAttributes.addFlashAttribute("passwordSuccess", "Votre mot de passe a été modifié avec succès.");
        }

        return "redirect:/profil?section=changePassword";
    }

    // updates the user's personal information after verifying their password, and redirects to the profile page
    @PostMapping("/update-info")
    public String updateUserInfo(@RequestParam("nom") String n, @RequestParam("prenom") String p, @RequestParam("email") String e, @RequestParam("tel") String t, @RequestParam("password") String password, RedirectAttributes r, Principal pr){
        String currentEmail = pr.getName();
        User user = userService.getUserByEmail(currentEmail);

        // verif du mot de passe
        if (!userService.verifPassword(user, password)) {
            r.addFlashAttribute("updateError", "Mot de passe incorrect.");
            return "redirect:/profil?section=editInfo";
        }



        // update des infos perso
        boolean updateInfo = userService.updateUserInfo(user, n, p, e, t);
        if (updateInfo) {
            r.addFlashAttribute("updateSuccess", "Informations modifiées avec succès.");
        } else {
            r.addFlashAttribute("updateError", "Erreur lors de la mise à jour des informations.");
        }

        return "redirect:/profil?section=editInfo";
    }
}
