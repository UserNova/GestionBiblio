package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Emprunt;
import com.example.GestionBiblio.repositories.EmpruntRepository;
import com.example.GestionBiblio.repositories.LivreRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EmpruntMvcController {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;

    public EmpruntMvcController(EmpruntRepository empruntRepository, LivreRepository livreRepository) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
    }

    @GetMapping("/emprunts")
    public String listEmprunts(
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {

        List<Emprunt> emprunts;
        if (statut != null && !statut.isBlank()) {
            emprunts = empruntRepository.findByStatutIgnoreCase(statut);
        } else if (start != null && end != null) {
            emprunts = empruntRepository.findByDateEmpruntBetween(start, end);
        } else {
            emprunts = empruntRepository.findAll();
        }

        model.addAttribute("emprunts", emprunts);
        model.addAttribute("statut", statut);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "emprunts";
    }

    @GetMapping("/emprunts/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("emprunt", new Emprunt());
        model.addAttribute("livres", livreRepository.findAll());
        return "emprunt-form";
    }

    @PostMapping("/emprunts")
    public String create(
            @ModelAttribute Emprunt emprunt,
            @RequestParam Long livreId
    ) {
        livreRepository.findById(livreId).ifPresent(emprunt::setLivre);
        empruntRepository.save(emprunt);
        return "redirect:/emprunts";
    }
}


