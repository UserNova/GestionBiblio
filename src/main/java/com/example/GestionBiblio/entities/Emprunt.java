package com.example.GestionBiblio.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_emrunt")
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;

    @Column(name = "status")
    private String statut;

    @Column(name = "emprunteur")
    private String emprunteur;

    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    // Constructeurs
    public Emprunt() {}
    public Emprunt(LocalDate dateEmprunt, LocalDate dateRetourPrevue, LocalDate dateRetourReelle, String statut, Livre livre) {
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourReelle = dateRetourReelle;
        this.statut = statut;
        this.livre = livre;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourReelle() {
        return dateRetourReelle;
    }

    public void setDateRetourReelle(LocalDate dateRetourReelle) {
        this.dateRetourReelle = dateRetourReelle;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(String emprunteur) {
        this.emprunteur = emprunteur;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    @PrePersist
    public void prePersist() {
        if (this.statut == null || this.statut.isBlank()) {
            this.statut = "en cours";
        }
        if (this.emprunteur == null || this.emprunteur.isBlank()) {
            this.emprunteur = "Inconnu";
        }
    }
}
