package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Livre;
import com.example.GestionBiblio.repositories.LivreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreRepository livreRepository;

    public LivreController(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    @GetMapping
    public List<Livre> getAll() {
        return livreRepository.findAll();
    }

    @GetMapping("/categorie/{categorie}")
    public List<Livre> getByCategorie(@PathVariable String categorie) {
        return livreRepository.findByCategorieIgnoreCase(categorie);
    }

    @GetMapping("/auteur/{auteurId}")
    public List<Livre> getByAuteur(@PathVariable Long auteurId) {
        return livreRepository.findByAuteurId(auteurId);
    }

    @GetMapping("/disponible/{disponible}")
    public List<Livre> getByDisponibilite(@PathVariable Boolean disponible) {
        return livreRepository.findByDisponible(disponible);
    }
}
