package web.projetdevwebavancer.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class TableRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nombreTables;
    private int nombrePersonnesMax;


    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    @OneToMany
    @JsonIgnore
    private List<TableReserver> tablesReserver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNombreTables() {
        return nombreTables;
    }

    public void setNombreTables(int nombreTables) {
        this.nombreTables = nombreTables;
    }

    public int getNombrePersonnesMax() {
        return nombrePersonnesMax;
    }

    public void setNombrePersonnesMax(int nombrePersonnesMax) {
        this.nombrePersonnesMax = nombrePersonnesMax;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<TableReserver> getTablesReserver() {
        return tablesReserver;
    }

    public void setTablesReserver(List<TableReserver> tablesReserver) {
        this.tablesReserver = tablesReserver;
    }
}
