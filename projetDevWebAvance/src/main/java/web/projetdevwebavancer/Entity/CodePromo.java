package web.projetdevwebavancer.Entity;

import jakarta.persistence.*;
import web.projetdevwebavancer.Entity.Restaurateur;

import java.time.LocalDate;
import java.util.List;

@Entity
public class CodePromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean active;
    private int nbMax;
    private int nbActuel;
    private float reduction;

    @OneToMany
    private List<Restaurateur> restaurateur;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getNbMax() {
        return nbMax;
    }

    public void setNbMax(int nbMax) {
        this.nbMax = nbMax;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Restaurateur> getRestaurateur() {
        return restaurateur;
    }

    public void setRestaurateur(List<Restaurateur> restaurateur) {
        this.restaurateur = restaurateur;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public float getReduction() {
        return reduction;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }

    public int getNbActuel() {
        return nbActuel;
    }

    public void setNbActuel(int nbActuel) {
        this.nbActuel = nbActuel;
    }
}
