package web.projetdevwebavancer.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user = null;

    private boolean abonnement;
    private int statut; //1: ongoing  ;  2: done ; 3 cancel
    private int prixTotal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignePanier = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAbonnement() {
        return abonnement;
    }

    public void setAbonnement(boolean abonnement) {
        this.abonnement = abonnement;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<LignePanier> getLignePanier() {
        return lignePanier;
    }

    public void setLignePanier(List<LignePanier> lignePanier) {
        this.lignePanier = lignePanier;
    }
}
