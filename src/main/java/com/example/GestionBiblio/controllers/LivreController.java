package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Livre;
import com.example.GestionBiblio.repositories.LivreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreRepository livreRepository;

    public LivreController(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    @GetMapping
    public String getAll(Model model) {
        List<Livre> livres = livreRepository.findAll();
        model.addAttribute("livres", livres);
        return "livres";
    }

    @GetMapping("/categorie/{categorie}")
    public String getByCategorie(@PathVariable String categorie, Model model) {
        List<Livre> livres = livreRepository.findByCategorieIgnoreCase(categorie);
        model.addAttribute("categorie", categorie);
        model.addAttribute("livres", livres);
        return "livres";
    }

    @GetMapping("/auteur/{auteurId}")
    public String getByAuteur(@PathVariable Long auteurId, Model model) {
        List<Livre> livres = livreRepository.findByAuteurId(auteurId);
        model.addAttribute("auteurId", auteurId);
        model.addAttribute("livres", livres);
        return "livres";
    }

    @GetMapping("/disponible/{disponible}")
    public String getByDisponibilite(@PathVariable Boolean disponible, Model model) {
        List<Livre> livres = livreRepository.findByDisponible(disponible);
        model.addAttribute("disponible", disponible);
        model.addAttribute("livres", livres);
        return "livres";
    }
}
