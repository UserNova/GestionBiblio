package com.example.GestionBiblio.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String isbn;
    private String categorie;
    private LocalDate dateParution;
    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts;

    public Livre() {}
    public Livre(String titre, String isbn, String categorie, LocalDate dateParution, Boolean disponible, Auteur auteur) {
        this.titre = titre;
        this.isbn = isbn;
        this.categorie = categorie;
        this.dateParution = dateParution;
        this.disponible = disponible;
        this.auteur = auteur;
    }


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

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public LocalDate getDateParution() {
        return dateParution;
    }

    public void setDateParution(LocalDate dateParution) {
        this.dateParution = dateParution;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }
    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }
}



