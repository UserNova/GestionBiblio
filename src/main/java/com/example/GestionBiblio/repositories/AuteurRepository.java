package com.example.GestionBiblio.repositories;

import com.example.GestionBiblio.entities.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    List<Auteur> findByNomContainingIgnoreCase(String nom);
}




