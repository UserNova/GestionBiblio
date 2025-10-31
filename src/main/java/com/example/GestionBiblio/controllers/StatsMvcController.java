package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Auteur;
import com.example.GestionBiblio.repositories.AuteurRepository;
import com.example.GestionBiblio.repositories.EmpruntRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StatsMvcController {

    private final EmpruntRepository empruntRepository;
    private final AuteurRepository auteurRepository;

    public StatsMvcController(EmpruntRepository empruntRepository, AuteurRepository auteurRepository) {
        this.empruntRepository = empruntRepository;
        this.auteurRepository = auteurRepository;
    }

    @GetMapping("/stats")
    public String stats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false, defaultValue = "5") int limit,
            Model model) {

        if (start == null) start = LocalDate.now().withDayOfMonth(1);
        if (end == null) end = LocalDate.now();

        List<Map<String, Object>> parMois = empruntRepository.countEmpruntsByMonth(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("month", ((Number) row[0]).intValue());
            m.put("total", ((Number) row[1]).longValue());
            return m;
        }).collect(Collectors.toList());

        List<Map<String, Object>> parCategorie = empruntRepository.countEmpruntsByCategory(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("categorie", (String) row[0]);
            m.put("total", ((Number) row[1]).longValue());
            return m;
        }).collect(Collectors.toList());

        List<Map<String, Object>> parStatut = empruntRepository.countEmpruntsByStatus(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("statut", (String) row[0]);
            m.put("total", ((Number) row[1]).longValue());
            return m;
        }).collect(Collectors.toList());

        List<Map<String, Object>> retardsParMois = empruntRepository.countOverdueAndTotalByMonth(start, end).stream().map(row -> {
            Map<String, Object> m = new HashMap<>();
            m.put("month", ((Number) row[0]).intValue());
            m.put("overdue", ((Number) row[1]).longValue());
            m.put("total", ((Number) row[2]).longValue());
            return m;
        }).collect(Collectors.toList());

        Map<Long, String> auteurNames = auteurRepository.findAll().stream()
                .collect(Collectors.toMap(Auteur::getId, Auteur::getNom));

        List<Map<String, Object>> topAuteurs = empruntRepository.topAuteursByEmprunts().stream()
                .limit(limit)
                .map(row -> {
                    Long auteurId = (Long) row[0];
                    Long total = ((Number) row[1]).longValue();
                    Map<String, Object> m = new HashMap<>();
                    m.put("nom", auteurNames.getOrDefault(auteurId, "Inconnu"));
                    m.put("total", total);
                    return m;
                }).collect(Collectors.toList());

        long total = empruntRepository.countAllEmprunts();
        long retards = empruntRepository.countRetards();
        double taux = total == 0 ? 0.0 : (retards * 100.0) / total;

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("parMois", parMois);
        model.addAttribute("parCategorie", parCategorie);
        model.addAttribute("parStatut", parStatut);
        model.addAttribute("retardsParMois", retardsParMois);
        model.addAttribute("topAuteurs", topAuteurs);
        model.addAttribute("totalEmprunts", total);
        model.addAttribute("retards", retards);
        model.addAttribute("tauxRetard", taux);
        model.addAttribute("limit", limit);
        return "stats";
    }
}



