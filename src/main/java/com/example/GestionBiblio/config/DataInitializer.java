package com.example.GestionBiblio.config;

import com.example.GestionBiblio.entities.Auteur;
import com.example.GestionBiblio.entities.Emprunt;
import com.example.GestionBiblio.entities.Livre;
import com.example.GestionBiblio.repositories.AuteurRepository;
import com.example.GestionBiblio.repositories.EmpruntRepository;
import com.example.GestionBiblio.repositories.LivreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleData(
            AuteurRepository auteurRepository,
            LivreRepository livreRepository,
            EmpruntRepository empruntRepository
    ) {
        return args -> {
            if (auteurRepository.count() > 0 || livreRepository.count() > 0 || empruntRepository.count() > 0) {
                return; // do not duplicate
            }

            Auteur a1 = auteurRepository.save(new Auteur("Victor Hugo", "Française"));
            Auteur a2 = auteurRepository.save(new Auteur("Jane Austen", "Britannique"));
            Auteur a3 = auteurRepository.save(new Auteur("Haruki Murakami", "Japonaise"));

            Livre l1 = new Livre("Les Misérables", "ISBN-001", "Roman", LocalDate.of(1862, 4, 3), true, a1);
            Livre l2 = new Livre("Orgueil et Préjugés", "ISBN-002", "Romance", LocalDate.of(1813, 1, 28), false, a2);
            Livre l3 = new Livre("Kafka sur le rivage", "ISBN-003", "Fantastique", LocalDate.of(2002, 9, 12), true, a3);
            livreRepository.saveAll(List.of(l1, l2, l3));

            Emprunt e1 = new Emprunt(LocalDate.now().minusDays(10), LocalDate.now().minusDays(2), null, "en cours", l2);
            Emprunt e2 = new Emprunt(LocalDate.now().minusMonths(1), LocalDate.now().minusDays(20), LocalDate.now().minusDays(15), "rendu", l1);
            Emprunt e3 = new Emprunt(LocalDate.now().minusDays(40), LocalDate.now().minusDays(30), LocalDate.now().minusDays(10), "rendu", l3);
            empruntRepository.saveAll(List.of(e1, e2, e3));
        };
    }
}




