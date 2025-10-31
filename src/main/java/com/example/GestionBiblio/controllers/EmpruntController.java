package com.example.GestionBiblio.controllers;

import com.example.GestionBiblio.entities.Emprunt;
import com.example.GestionBiblio.entities.Livre;
import com.example.GestionBiblio.repositories.EmpruntRepository;
import com.example.GestionBiblio.repositories.LivreRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;

    public EmpruntController(EmpruntRepository empruntRepository, LivreRepository livreRepository) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
    }

    @GetMapping
    public List<Emprunt> getAll() {
        return empruntRepository.findAll();
    }

    @GetMapping("/statut/{statut}")
    public List<Emprunt> getByStatut(@PathVariable String statut) {
        return empruntRepository.findByStatutIgnoreCase(statut);
    }

    @GetMapping("/periode")
    public List<Emprunt> getByPeriode(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return empruntRepository.findByDateEmpruntBetween(start, end);
    }

    // Seed fake loans per book for demo/testing
    @GetMapping("/seed")
    public String seed(
            @RequestParam(name = "perLivre", defaultValue = "1") int perLivre,
            @RequestParam(name = "months", defaultValue = "6") int months) {
        List<Livre> livres = livreRepository.findAll();
        int created = 0;
        Random rand = ThreadLocalRandom.current();
        LocalDate today = LocalDate.now();
        LocalDate startWindow = today.minusMonths(Math.max(months, 1));

        for (Livre livre : livres) {
            for (int i = 0; i < Math.max(perLivre, 1); i++) {
                long days = ChronoUnit.DAYS.between(startWindow, today);
                long r = days > 0 ? rand.nextLong(days + 1) : 0;
                LocalDate dateEmprunt = startWindow.plusDays(r);
                LocalDate datePrevue = dateEmprunt.plusDays(14 + rand.nextInt(7));

                Emprunt e = new Emprunt();
                e.setLivre(livre);
                e.setDateEmprunt(dateEmprunt);
                e.setDateRetourPrevue(datePrevue);

                // 60% returned, 40% still ongoing
                if (rand.nextDouble() < 0.6) {
                    // 50% on time, 50% late by up to 10 days
                    int delta = rand.nextBoolean() ? -rand.nextInt(3) : rand.nextInt(11);
                    LocalDate retourReelle = datePrevue.plusDays(delta);
                    e.setDateRetourReelle(retourReelle);
                    e.setStatut("rendu");
                } else {
                    e.setDateRetourReelle(null);
                    e.setStatut("en cours");
                }

                // random emprunteur name
                e.setEmprunteur("Client " + (rand.nextInt(900) + 100));

                empruntRepository.save(e);
                created++;
            }
        }
        return "Seeded emprunts: " + created + " for livres: " + livres.size();
    }
}




