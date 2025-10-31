package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Livre;
import com.example.GestionBiblio.repositories.AuteurRepository;
import com.example.GestionBiblio.repositories.LivreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LivreMvcController {

    private final LivreRepository livreRepository;
    private final AuteurRepository auteurRepository;

    public LivreMvcController(LivreRepository livreRepository, AuteurRepository auteurRepository) {
        this.livreRepository = livreRepository;
        this.auteurRepository = auteurRepository;
    }

    @GetMapping({"/", "/livres"})
    public String listLivres(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) Long auteurId,
            @RequestParam(required = false) Boolean disponible,
            Model model) {

        List<Livre> livres;
        if (categorie != null && !categorie.isBlank()) {
            livres = livreRepository.findByCategorieIgnoreCase(categorie);
        } else if (auteurId != null) {
            livres = livreRepository.findByAuteurId(auteurId);
        } else if (disponible != null) {
            livres = livreRepository.findByDisponible(disponible);
        } else {
            livres = livreRepository.findAll();
        }

        model.addAttribute("livres", livres);
        model.addAttribute("auteurs", auteurRepository.findAll());
        model.addAttribute("categorie", categorie);
        model.addAttribute("auteurId", auteurId);
        model.addAttribute("disponible", disponible);
        return "livres";
    }

    @GetMapping("/livres/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("livre", new Livre());
        model.addAttribute("auteurs", auteurRepository.findAll());
        return "livre-form";
    }

    @PostMapping("/livres")
    public String create(@ModelAttribute Livre livre, @RequestParam(required = false) Long auteurId) {
        if (auteurId != null) {
            auteurRepository.findById(auteurId).ifPresent(livre::setAuteur);
        }
        livreRepository.save(livre);
        return "redirect:/livres";
    }

    @GetMapping("/livres/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        return livreRepository.findById(id).map(l -> {
            model.addAttribute("livre", l);
            model.addAttribute("auteurs", auteurRepository.findAll());
            return "livre-form";
        }).orElse("redirect:/livres");
    }

    @PostMapping("/livres/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Livre form, @RequestParam(required = false) Long auteurId) {
        return livreRepository.findById(id).map(existing -> {
            existing.setTitre(form.getTitre());
            existing.setIsbn(form.getIsbn());
            existing.setCategorie(form.getCategorie());
            existing.setDateParution(form.getDateParution());
            existing.setDisponible(form.getDisponible());
            if (auteurId != null) {
                auteurRepository.findById(auteurId).ifPresent(existing::setAuteur);
            } else {
                existing.setAuteur(null);
            }
            livreRepository.save(existing);
            return "redirect:/livres";
        }).orElse("redirect:/livres");
    }

    @PostMapping("/livres/{id}/delete")
    public String delete(@PathVariable Long id) {
        livreRepository.deleteById(id);
        return "redirect:/livres";
    }
}


