package com.example.GestionBiblio.repositories;

import com.example.GestionBiblio.entities.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByCategorieIgnoreCase(String categorie);
    List<Livre> findByAuteurId(Long auteurId);
    List<Livre> findByDisponible(Boolean disponible);
}