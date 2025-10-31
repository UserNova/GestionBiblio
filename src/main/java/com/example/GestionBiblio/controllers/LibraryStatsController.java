package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Auteur;
import com.example.GestionBiblio.repositories.AuteurRepository;
import com.example.GestionBiblio.repositories.EmpruntRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class LibraryStatsController {

    private final EmpruntRepository empruntRepository;
    private final AuteurRepository auteurRepository;

    public LibraryStatsController(EmpruntRepository empruntRepository, AuteurRepository auteurRepository) {
        this.empruntRepository = empruntRepository;
        this.auteurRepository = auteurRepository;
    }

    @GetMapping("/emprunts/mois")
    public List<Map<String, Object>> empruntsParMois(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return empruntRepository.countEmpruntsByMonth(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("month", ((Number) row[0]).intValue());
            m.put("total", ((Number) row[1]).longValue());
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/auteurs/top")
    public List<Map<String, Object>> topAuteurs(@RequestParam(name = "limit", defaultValue = "5") int limit) {
        Map<Long, String> auteurNames = auteurRepository.findAll().stream()
                .collect(Collectors.toMap(Auteur::getId, Auteur::getNom));

        return empruntRepository.topAuteursByEmprunts().stream()
                .limit(limit)
                .map(row -> {
                    Long auteurId = (Long) row[0];
                    Long total = ((Number) row[1]).longValue();
                    Map<String, Object> m = new HashMap<>();
                    m.put("auteurId", auteurId);
                    m.put("nom", auteurNames.getOrDefault(auteurId, "Inconnu"));
                    m.put("total", total);
                    return m;
                }).collect(Collectors.toList());
    }

    @GetMapping("/emprunts/taux-retard")
    public Map<String, Object> tauxRetard() {
        long total = empruntRepository.countAllEmprunts();
        long retards = empruntRepository.countRetards();
        double taux = total == 0 ? 0.0 : (retards * 100.0) / total;
        Map<String, Object> m = new HashMap<>();
        m.put("totalEmprunts", total);
        m.put("retards", retards);
        m.put("tauxRetardPourcent", taux);
        return m;
    }

    // New endpoints
    @GetMapping("/emprunts/categories")
    public List<Map<String, Object>> empruntsParCategorie(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return empruntRepository.countEmpruntsByCategory(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("categorie", (String) row[0]);
            m.put("total", ((Number) row[1]).longValue());
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/emprunts/status")
    public List<Map<String, Object>> empruntsParStatut(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return empruntRepository.countEmpruntsByStatus(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("statut", (String) row[0]);
            m.put("total", ((Number) row[1]).longValue());
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/emprunts/overdue/mois")
    public List<Map<String, Object>> retardsParMois(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return empruntRepository.countOverdueAndTotalByMonth(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("month", ((Number) row[0]).intValue());
            m.put("overdue", ((Number) row[1]).longValue());
            m.put("total", ((Number) row[2]).longValue());
            return m;
        }).collect(Collectors.toList());
    }
}



