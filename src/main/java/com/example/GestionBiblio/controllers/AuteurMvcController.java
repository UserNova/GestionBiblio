package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Auteur;
import com.example.GestionBiblio.repositories.AuteurRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuteurMvcController {

    private final AuteurRepository auteurRepository;

    public AuteurMvcController(AuteurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
    }

    @GetMapping("/auteurs")
    public String list(Model model) {
        model.addAttribute("auteurs", auteurRepository.findAll());
        return "auteurs";
    }

    @GetMapping("/auteurs/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("auteur", new Auteur());
        return "auteur-form";
    }

    @PostMapping("/auteurs")
    public String create(@ModelAttribute Auteur auteur) {
        auteurRepository.save(auteur);
        return "redirect:/auteurs";
    }

    @GetMapping("/auteurs/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Auteur> a = auteurRepository.findById(id);
        if (a.isEmpty()) return "redirect:/auteurs";
        model.addAttribute("auteur", a.get());
        return "auteur-form";
    }

    @PostMapping("/auteurs/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Auteur form) {
        return auteurRepository.findById(id).map(existing -> {
            existing.setNom(form.getNom());
            existing.setNationalite(form.getNationalite());
            auteurRepository.save(existing);
            return "redirect:/auteurs";
        }).orElse("redirect:/auteurs");
    }

    @PostMapping("/auteurs/{id}/delete")
    public String delete(@PathVariable Long id) {
        auteurRepository.deleteById(id);
        return "redirect:/auteurs";
    }
}



