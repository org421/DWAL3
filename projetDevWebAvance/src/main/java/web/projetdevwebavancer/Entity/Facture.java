package web.projetdevwebavancer.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private User idClient;
    @ManyToOne
    @JsonIgnore
    private Restaurant idRestaurant;

    @ManyToMany
    @JsonIgnore
    private List<Plat> platsAchat;
    @ManyToMany
    @JsonIgnore
    private List<Menu> menuAchat;

    private int prixTTC;

    private LocalDate dateFacture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getIdClient() {
        return idClient;
    }

    public void setIdClient(User idClient) {
        this.idClient = idClient;
    }

    public Restaurant getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Restaurant idRestaurant) {
        this.idRestaurant = idRestaurant;
    }


    public List<Plat> getPlatsAchat() {
        return platsAchat;
    }

    public void setPlatsAchat(List<Plat> platsAchat) {
        this.platsAchat = platsAchat;
    }

    public List<Menu> getMenuAchat() {
        return menuAchat;
    }

    public void setMenuAchat(List<Menu> menuAchat) {
        this.menuAchat = menuAchat;
    }

    public int getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(int prixTTC) {
        this.prixTTC = prixTTC;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }
}
