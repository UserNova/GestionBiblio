package com.example.GestionBiblio.repositories;

import com.example.GestionBiblio.entities.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByStatutIgnoreCase(String statut);

    List<Emprunt> findByDateEmpruntBetween(LocalDate start, LocalDate end);

    @Query("select extract(month from e.dateEmprunt) as month, count(e) as total " +
            "from Emprunt e where e.dateEmprunt between :start and :end " +
            "group by extract(month from e.dateEmprunt) order by month")
    List<Object[]> countEmpruntsByMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select e.livre.auteur.id, count(e) as total from Emprunt e " +
            "group by e.livre.auteur.id order by total desc")
    List<Object[]> topAuteursByEmprunts();

    @Query("select count(e) from Emprunt e where e.dateRetourReelle is not null and e.dateRetourReelle > e.dateRetourPrevue")
    long countRetards();

    @Query("select count(e) from Emprunt e")
    long countAllEmprunts();

    // New aggregations
    @Query("select e.livre.categorie as categorie, count(e) as total " +
            "from Emprunt e where e.dateEmprunt between :start and :end " +
            "group by e.livre.categorie order by total desc")
    List<Object[]> countEmpruntsByCategory(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select e.statut as statut, count(e) as total " +
            "from Emprunt e where e.dateEmprunt between :start and :end " +
            "group by e.statut order by total desc")
    List<Object[]> countEmpruntsByStatus(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select extract(month from coalesce(e.dateRetourReelle, e.dateRetourPrevue)) as month, " +
            "sum(case when e.dateRetourReelle is not null and e.dateRetourReelle > e.dateRetourPrevue then 1 else 0 end) as overdue, " +
            "count(e) as total " +
            "from Emprunt e where e.dateEmprunt between :start and :end " +
            "group by extract(month from coalesce(e.dateRetourReelle, e.dateRetourPrevue)) order by month")
    List<Object[]> countOverdueAndTotalByMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);
}




