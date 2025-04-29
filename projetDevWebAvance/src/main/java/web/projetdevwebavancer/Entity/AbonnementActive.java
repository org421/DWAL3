package web.projetdevwebavancer.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AbonnementActive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Abonnement abonnement;

    @OneToOne
    private CodePromo codePromo;

    @OneToOne
    private Restaurateur restaurateur;

    private LocalDate dateFin;
    private int prixPaye;
    private int moyenPaiement;
    private boolean renouvellement;

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public CodePromo getCodePromo() {
        return codePromo;
    }

    public void setCodePromo(CodePromo codePromo) {
        this.codePromo = codePromo;
    }

    public Restaurateur getRestaurateur() {
        return restaurateur;
    }

    public void setRestaurateur(Restaurateur restaurateur) {
        this.restaurateur = restaurateur;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getPrixPaye() {
        return prixPaye;
    }

    public void setPrixPaye(int prixPaye) {
        this.prixPaye = prixPaye;
    }

    public int getMoyenPaiement() {
        return moyenPaiement;
    }

    public void setMoyenPaiement(int moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
    }

    public boolean isRenouvellement() {
        return renouvellement;
    }

    public void setRenouvellement(boolean renouvellement) {
        this.renouvellement = renouvellement;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
