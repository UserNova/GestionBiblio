package com.example.GestionBiblio.entities;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String nationalite;

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    private List<Livre> livres;

    // Constructeurs
    public Auteur() {}
    public Auteur(String nom, String nationalite) {
        this.nom = nom;
        this.nationalite = nationalite;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }
}


