package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Auteur;
import com.example.GestionBiblio.repositories.AuteurRepository;
import com.example.GestionBiblio.repositories.EmpruntRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/stats")
public class LibraryStatsController {

    private final EmpruntRepository empruntRepository;
    private final AuteurRepository auteurRepository;

    public LibraryStatsController(EmpruntRepository empruntRepository, AuteurRepository auteurRepository) {
        this.empruntRepository = empruntRepository;
        this.auteurRepository = auteurRepository;
    }

    @GetMapping("/emprunts/mois")
    public String empruntsParMois(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {
        return "redirect:/stats?start=" + start + "&end=" + end;
    }

    @GetMapping("/auteurs/top")
    public String topAuteurs(@RequestParam(name = "limit", defaultValue = "5") int limit, Model model) {
        return "redirect:/stats?limit=" + limit;
    }

    @GetMapping("/emprunts/taux-retard")
    public String tauxRetard(Model model) {
        return "redirect:/stats";
    }

    // New endpoints
    @GetMapping("/emprunts/categories")
    public String empruntsParCategorie(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {
        return "redirect:/stats?start=" + start + "&end=" + end;
    }

    @GetMapping("/emprunts/status")
    public String empruntsParStatut(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {
        return "redirect:/stats?start=" + start + "&end=" + end;
    }

    @GetMapping("/emprunts/overdue/mois")
    public String retardsParMois(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {
        return "redirect:/stats?start=" + start + "&end=" + end;
    }
}



