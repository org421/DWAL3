package web.projetdevwebavancer.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ticket {

    @Transient
    public int OUVERT = 1;
    @Transient
    public int FERMER = 0;
    @Transient
    public int FAIBLE = 0;
    @Transient
    public int MOYENNE = 1;
    @Transient
    public int ELEVER = 2;




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private int status;
    private int prioriter;                      //On pourrais utiliser cela pour mettre en evidence la priorité
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    @ManyToOne
    @JsonIgnore
    private User idClient;

    @OneToMany
    private List<Message> chat = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrioriter() {
        return prioriter;
    }

    public void setPrioriter(int prioriter) {
        this.prioriter = prioriter;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public User getIdClient() {
        return idClient;
    }

    public void setIdClient(User idClient) {
        this.idClient = idClient;
    }

    public List<Message> getChat() {
        return chat;
    }

    public void setChat(List<Message> chat) {
        this.chat = chat;
    }
}
