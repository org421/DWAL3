package web.projetdevwebavancer.Entity;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalTime;

@Entity
public class Horaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Restaurant restaurant;

    //jour d'ouverture
    private boolean lundi = false;
    private boolean mardi = false;
    private boolean mercredi = false;
    private boolean jeudi = false;
    private boolean vendredi = false;
    private boolean samedi = false;
    private boolean dimanche = false;

    //horaire d'ouverture
    private LocalTime horaireOuvertureLundi1;
    private LocalTime horaireOuvertureLundi2;
    private LocalTime horaireFermetureLundi1;
    private LocalTime horaireFermetureLundi2;

    private LocalTime horaireOuvertureMardi1;
    private LocalTime horaireOuvertureMardi2;
    private LocalTime horaireFermetureMardi1;
    private LocalTime horaireFermetureMardi2;

    private LocalTime horaireOuvertureMercredi1;
    private LocalTime horaireOuvertureMercredi2;
    private LocalTime horaireFermetureMercredi1;
    private LocalTime horaireFermetureMercredi2;

    private LocalTime horaireOuvertureJeudi1;
    private LocalTime horaireOuvertureJeudi2;
    private LocalTime horaireFermetureJeudi1;
    private LocalTime horaireFermetureJeudi2;

    private LocalTime horaireOuvertureVendredi1;
    private LocalTime horaireOuvertureVendredi2;
    private LocalTime horaireFermetureVendredi1;
    private LocalTime horaireFermetureVendredi2;

    private LocalTime horaireOuvertureSamedi1;
    private LocalTime horaireOuvertureSamedi2;
    private LocalTime horaireFermetureSamedi1;
    private LocalTime horaireFermetureSamedi2;

    private LocalTime horaireOuvertureDimanche1;
    private LocalTime horaireOuvertureDimanche2;
    private LocalTime horaireFermetureDimanche1;
    private LocalTime horaireFermetureDimanche2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isLundi() {
        return lundi;
    }

    public void setLundi(boolean lundi) {
        this.lundi = lundi;
    }

    public boolean isMardi() {
        return mardi;
    }

    public void setMardi(boolean mardi) {
        this.mardi = mardi;
    }

    public boolean isMercredi() {
        return mercredi;
    }

    public void setMercredi(boolean mercredi) {
        this.mercredi = mercredi;
    }

    public boolean isJeudi() {
        return jeudi;
    }

    public void setJeudi(boolean jeudi) {
        this.jeudi = jeudi;
    }

    public boolean isVendredi() {
        return vendredi;
    }

    public void setVendredi(boolean vendredi) {
        this.vendredi = vendredi;
    }

    public boolean isSamedi() {
        return samedi;
    }

    public void setSamedi(boolean samedi) {
        this.samedi = samedi;
    }

    public boolean isDimanche() {
        return dimanche;
    }

    public void setDimanche(boolean dimanche) {
        this.dimanche = dimanche;
    }

    public LocalTime getHoraireOuvertureLundi1() {
        return horaireOuvertureLundi1;
    }

    public void setHoraireOuvertureLundi1(LocalTime horaireOuvertureLundi1) {
        this.horaireOuvertureLundi1 = horaireOuvertureLundi1;
    }

    public LocalTime getHoraireOuvertureLundi2() {
        return horaireOuvertureLundi2;
    }

    public void setHoraireOuvertureLundi2(LocalTime horaireOuvertureLundi2) {
        this.horaireOuvertureLundi2 = horaireOuvertureLundi2;
    }

    public LocalTime getHoraireFermetureLundi1() {
        return horaireFermetureLundi1;
    }

    public void setHoraireFermetureLundi1(LocalTime horaireFermetureLundi1) {
        this.horaireFermetureLundi1 = horaireFermetureLundi1;
    }

    public LocalTime getHoraireFermetureLundi2() {
        return horaireFermetureLundi2;
    }

    public void setHoraireFermetureLundi2(LocalTime horaireFermetureLundi2) {
        this.horaireFermetureLundi2 = horaireFermetureLundi2;
    }

    public LocalTime getHoraireOuvertureMardi1() {
        return horaireOuvertureMardi1;
    }

    public void setHoraireOuvertureMardi1(LocalTime horaireOuvertureMardi1) {
        this.horaireOuvertureMardi1 = horaireOuvertureMardi1;
    }

    public LocalTime getHoraireOuvertureMardi2() {
        return horaireOuvertureMardi2;
    }

    public void setHoraireOuvertureMardi2(LocalTime horaireOuvertureMardi2) {
        this.horaireOuvertureMardi2 = horaireOuvertureMardi2;
    }

    public LocalTime getHoraireFermetureMardi1() {
        return horaireFermetureMardi1;
    }

    public void setHoraireFermetureMardi1(LocalTime horaireFermetureMardi1) {
        this.horaireFermetureMardi1 = horaireFermetureMardi1;
    }

    public LocalTime getHoraireFermetureMardi2() {
        return horaireFermetureMardi2;
    }

    public void setHoraireFermetureMardi2(LocalTime horaireFermetureMardi2) {
        this.horaireFermetureMardi2 = horaireFermetureMardi2;
    }

    public LocalTime getHoraireOuvertureMercredi1() {
        return horaireOuvertureMercredi1;
    }

    public void setHoraireOuvertureMercredi1(LocalTime horaireOuvertureMercredi1) {
        this.horaireOuvertureMercredi1 = horaireOuvertureMercredi1;
    }

    public LocalTime getHoraireOuvertureMercredi2() {
        return horaireOuvertureMercredi2;
    }

    public void setHoraireOuvertureMercredi2(LocalTime horaireOuvertureMercredi2) {
        this.horaireOuvertureMercredi2 = horaireOuvertureMercredi2;
    }

    public LocalTime getHoraireFermetureMercredi1() {
        return horaireFermetureMercredi1;
    }

    public void setHoraireFermetureMercredi1(LocalTime horaireFermetureMercredi1) {
        this.horaireFermetureMercredi1 = horaireFermetureMercredi1;
    }

    public LocalTime getHoraireFermetureMercredi2() {
        return horaireFermetureMercredi2;
    }

    public void setHoraireFermetureMercredi2(LocalTime horaireFermetureMercredi2) {
        this.horaireFermetureMercredi2 = horaireFermetureMercredi2;
    }

    public LocalTime getHoraireOuvertureJeudi1() {
        return horaireOuvertureJeudi1;
    }

    public void setHoraireOuvertureJeudi1(LocalTime horaireOuvertureJeudi1) {
        this.horaireOuvertureJeudi1 = horaireOuvertureJeudi1;
    }

    public LocalTime getHoraireOuvertureJeudi2() {
        return horaireOuvertureJeudi2;
    }

    public void setHoraireOuvertureJeudi2(LocalTime horaireOuvertureJeudi2) {
        this.horaireOuvertureJeudi2 = horaireOuvertureJeudi2;
    }

    public LocalTime getHoraireFermetureJeudi1() {
        return horaireFermetureJeudi1;
    }

    public void setHoraireFermetureJeudi1(LocalTime horaireFermetureJeudi1) {
        this.horaireFermetureJeudi1 = horaireFermetureJeudi1;
    }

    public LocalTime getHoraireFermetureJeudi2() {
        return horaireFermetureJeudi2;
    }

    public void setHoraireFermetureJeudi2(LocalTime horaireFermetureJeudi2) {
        this.horaireFermetureJeudi2 = horaireFermetureJeudi2;
    }

    public LocalTime getHoraireOuvertureVendredi1() {
        return horaireOuvertureVendredi1;
    }

    public void setHoraireOuvertureVendredi1(LocalTime horaireOuvertureVendredi1) {
        this.horaireOuvertureVendredi1 = horaireOuvertureVendredi1;
    }

    public LocalTime getHoraireOuvertureVendredi2() {
        return horaireOuvertureVendredi2;
    }

    public void setHoraireOuvertureVendredi2(LocalTime horaireOuvertureVendredi2) {
        this.horaireOuvertureVendredi2 = horaireOuvertureVendredi2;
    }

    public LocalTime getHoraireFermetureVendredi1() {
        return horaireFermetureVendredi1;
    }

    public void setHoraireFermetureVendredi1(LocalTime horaireFermetureVendredi1) {
        this.horaireFermetureVendredi1 = horaireFermetureVendredi1;
    }

    public LocalTime getHoraireFermetureVendredi2() {
        return horaireFermetureVendredi2;
    }

    public void setHoraireFermetureVendredi2(LocalTime horaireFermetureVendredi2) {
        this.horaireFermetureVendredi2 = horaireFermetureVendredi2;
    }

    public LocalTime getHoraireOuvertureSamedi1() {
        return horaireOuvertureSamedi1;
    }

    public void setHoraireOuvertureSamedi1(LocalTime horaireOuvertureSamedi1) {
        this.horaireOuvertureSamedi1 = horaireOuvertureSamedi1;
    }

    public LocalTime getHoraireOuvertureSamedi2() {
        return horaireOuvertureSamedi2;
    }

    public void setHoraireOuvertureSamedi2(LocalTime horaireOuvertureSamedi2) {
        this.horaireOuvertureSamedi2 = horaireOuvertureSamedi2;
    }

    public LocalTime getHoraireFermetureSamedi1() {
        return horaireFermetureSamedi1;
    }

    public void setHoraireFermetureSamedi1(LocalTime horaireFermetureSamedi1) {
        this.horaireFermetureSamedi1 = horaireFermetureSamedi1;
    }

    public LocalTime getHoraireFermetureSamedi2() {
        return horaireFermetureSamedi2;
    }

    public void setHoraireFermetureSamedi2(LocalTime horaireFermetureSamedi2) {
        this.horaireFermetureSamedi2 = horaireFermetureSamedi2;
    }

    public LocalTime getHoraireOuvertureDimanche1() {
        return horaireOuvertureDimanche1;
    }

    public void setHoraireOuvertureDimanche1(LocalTime horaireOuvertureDimanche1) {
        this.horaireOuvertureDimanche1 = horaireOuvertureDimanche1;
    }

    public LocalTime getHoraireOuvertureDimanche2() {
        return horaireOuvertureDimanche2;
    }

    public void setHoraireOuvertureDimanche2(LocalTime horaireOuvertureDimanche2) {
        this.horaireOuvertureDimanche2 = horaireOuvertureDimanche2;
    }

    public LocalTime getHoraireFermetureDimanche1() {
        return horaireFermetureDimanche1;
    }

    public void setHoraireFermetureDimanche1(LocalTime horaireFermetureDimanche1) {
        this.horaireFermetureDimanche1 = horaireFermetureDimanche1;
    }

    public LocalTime getHoraireFermetureDimanche2() {
        return horaireFermetureDimanche2;
    }

    public void setHoraireFermetureDimanche2(LocalTime horaireFermetureDimanche2) {
        this.horaireFermetureDimanche2 = horaireFermetureDimanche2;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
