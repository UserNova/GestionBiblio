
# 1. Description du projet
L’application permet de gérer les livres, leurs auteurs et les emprunts, tout en affichant les statuts (disponible / emprunté).

# 2. Architecture technique
## 2.1 Stack technologique
- Backend : Spring Boot 3.x
- ORM : Spring Data JPA / Hibernate
- Frontend : Thymeleaf, HTML5, CSS3, Bootstrap 5
- Base de données : MySQL
- Build : Maven

## 2.2 Structure du code

<img width="466" height="862" alt="image1" src="https://github.com/user-attachments/assets/20f73ed2-fbca-4eee-8a87-c96c08215803" />


## 2.3 Diagramme d’architecture (texte ou image)
Navigateur 🠖 Contrôleur Spring 🠖 Service 🠖 Repository 🠖 Base MySQL 🠖 Vue Thymeleaf

<img width="1920" height="1080" alt="Navigateur 🠖 Contrôleur Spring 🠖 Service 🠖 Repository 🠖 Base MySQL 🠖 Vue Thymeleaf" src="https://github.com/user-attachments/assets/7c53c38e-ae78-4c10-83c1-2cb1c39c382d" />



# 3. Fonctionnalités principales
3. Fonctionnalités principales

✅ Gestion des livres
- Ajouter, modifier, supprimer et consulter des livres.
- Indiquer leur disponibilité.
- Associer chaque livre à un auteur et à une catégorie.

✅ Gestion des auteurs
- Ajouter, modifier ou supprimer un auteur.
- Afficher la liste complète des auteurs.

✅ Gestion des emprunts
- Enregistrer un nouvel emprunt (date, retour prévu/réel, statut).
- Mettre à jour le statut d’un emprunt (en cours / rendu).

✅ Recherche et filtrage
- Rechercher par titre, auteur, catégorie ou disponibilité.

✅ Statistiques (optionnel)
- Nombre total de livres disponibles.
- Nombre de livres empruntés.
- Classement des auteurs les plus lus.

# 4. Modèle de données
## 4.1 Entités

Livre :
    id : Long
    titre : String
    isbn : String
    categorie : String
    dateParution : LocalDate
    disponible : Boolean
    auteur : Auteur

Auteur :
    id : Long
    nom : String
    nationalite : String

Emprunt :
    id : Long
    dateEmprunt : LocalDate
    dateRetourPrevue : LocalDate
    dateRetourReelle : LocalDate
    status : String
    livre : Livre

## 4.2 Relations
Les relations sont les suivantes :
Un Livre appartient à un Auteur via une relation @ManyToOne, et un Auteur possède plusieurs livres via @OneToMany(mappedBy="auteur").
Un Emprunt référence un Livre via une relation @ManyToOne.

## 4.3 Configuration base de données
spring.datasource.url=jdbc:mysql://localhost:3306/gestionbiblio?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

# 5. Lancer le projet
## 5.1 Prérequis
- Java version requise.
- Maven.

## 5.2 Installation
- Cloner le dépôt.
- Configurer `application.properties` / `application.yml`.
- Lancer l'application (`mvn spring-boot:run` ou exécuter la classe main).

## 5.3 Accès
Page d’accueil : http://localhost:8080/
Page statique : http://localhost:8080/stats

# 6. Jeu de données initial (optionnel)
- Chargement automatique avec un composant DataInitializer :

Exemple de livres créés :

| Titre          | Auteur        | Disponible |
| -------------- | ------------- | ---------- |
| Les Misérables | Victor Hugo   | Oui        |
| 1984           | George Orwell | Oui        |
| La Peste       | Albert Camus  | Non        |

Exemple d’emprunt :

| Livre    | Date emprunt | Retour prévue | Statut   |
| -------- | ------------ | ------------- | -------- |
| 1984     | 2025-10-10   | 2025-10-25    | rendu    |
| La Peste | 2025-10-20   | 2025-11-05    | en cours |

# 7. Démonstration (Vidéo)
- Lien vers la vidéo de démonstration de l'application.

 https://www.youtube.com/watch?v=cOTPlbBBpxE

- Une courte vidéo montre :
    L’ajout d’un nouveau livre.
    L’ajout d’un emprunt.
    L'ajoit d'un auteur.
    Suppression et modification.
    L’affichage des statistiques.

# 8. Auteurs / Encadrement
Nom : Rania ZHIRI
Encadrant : Mohamed Lachgar
Établissement : École Normale Supérieure de Marrakech
Module : Développement JakartaEE : Spring
