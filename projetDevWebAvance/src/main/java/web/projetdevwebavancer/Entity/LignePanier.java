package web.projetdevwebavancer.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class LignePanier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Panier panier;

    @OneToOne
    private Abonnement abonnement;

    @ManyToOne
    private Plat plat;

    @ManyToOne
    private Menu menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Abonnement getAbonnement() { return abonnement; }

    public void setAbonnement(Abonnement abonnement) { this.abonnement = abonnement; }

    public Menu getMenu() { return menu; }

    public void setMenu(Menu menu) { this.menu = menu; }

    public Plat getPlat() {return plat;}

    public void setPlat(Plat plat) {
        this.plat = plat;
    }
}
