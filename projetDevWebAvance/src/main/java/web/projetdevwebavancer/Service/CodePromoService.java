package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.CodePromo;
import web.projetdevwebavancer.Repository.CodePromoRepository;

import java.time.LocalDate;

@Service
public class CodePromoService {
    @Autowired
    CodePromoRepository codePromoRepository;

    // Creates and saves a new promo code with the specified code, maximum usage, start date, and end date
    public void saveCodePromo(String code, int nbMax, LocalDate dateDebut, LocalDate dateFin, float reduction) {
        CodePromo codePromo = new CodePromo();
        codePromo.setCode(code);
        codePromo.setNbMax(nbMax);
        codePromo.setDateDebut(dateDebut);
        codePromo.setDateFin(dateFin);
        codePromo.setReduction(reduction);
        codePromo.setActive(true);
        codePromo.setNbActuel(0);

        codePromoRepository.save(codePromo);
    }
}
