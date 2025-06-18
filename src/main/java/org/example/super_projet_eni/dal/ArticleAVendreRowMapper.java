package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleAVendreRowMapper implements RowMapper<ArticleAVendre> {
    @Override
    public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
        var articleAVendre = new ArticleAVendre();
        articleAVendre.setId(rs.getInt("no_article"));
        articleAVendre.setNom(rs.getString("nom_article"));
        articleAVendre.setDescription(rs.getString("description"));
        articleAVendre.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
        articleAVendre.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
        articleAVendre.setStatut(rs.getInt("statut_enchere"));
        articleAVendre.setPrixInitial(rs.getInt("prix_initial"));
        articleAVendre.setPrixVente(rs.getInt("prix_vente"));
        articleAVendre.setPhoto(rs.getInt("photo"));

        // Association pour l'utilisateur
        Utilisateur vendeur = new Utilisateur();
        vendeur.setPseudo(rs.getString("id_utilisateur"));
        articleAVendre.setVendeur(vendeur);

        // Association pour la catégorie
        Categorie categorie = new Categorie();
        categorie.setId(rs.getInt("no_categorie"));
        articleAVendre.setCategorie(categorie);

        // Association pour l'adresse
        Adresse adresse = new Adresse();
        adresse.setId(rs.getInt("no_adresse_retrait"));
        articleAVendre.setRetrait(adresse);

        return articleAVendre;
    }
}
