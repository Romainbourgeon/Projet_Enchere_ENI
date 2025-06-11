package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Categorie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class categorieTestCrud {

        // Injection de dépendance de l'interface categorieDao
        // Spring Boot va automatiquement injecter l'implémentation de DragonDao
        @Autowired
        private CategorieDao categorieDao;

        // Test pour la création et la lecture d'un dragon
        @Test
        public void testCreateAndRead() {
            // Création d'un nouvel objet Dragon
            Categorie newCategorie = new Categorie();
            newCategorie.setLibelle("Livres");

            // Appel de la méthode create pour insérer la catégorie dans la base de données
            // La méthode create retourne l'ID généré pour le dragon inséré
            Long categorieId = categorieDao.create(newCategorie);

            // Appel de la méthode read pour récupérer le dragon de la base de données
            Categorie retrievedCategorie = categorieDao.read(categorieId);

            // Vérification que le dragon récupéré n'est pas null
            assertNotNull(retrievedCategorie, "La catégorie récupérée ne devrait pas être null");

            // Vérification que les propriétés du dragon récupéré correspondent à celles du dragon inséré
            assertEquals("Livres", retrievedCategorie.getLibelle(), "Le nom de la catégorie devrait être Livres");
        }




}
