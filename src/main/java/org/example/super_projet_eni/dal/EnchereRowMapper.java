package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Enchere;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnchereRowMapper implements RowMapper<Enchere> {
    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        var enchere = new Enchere();
        enchere.setDate(rs.getDate("date_enchere").toLocalDate().atStartOfDay());
        enchere.setMontant(rs.getInt("montant_enchere"));

        // Association pour l'utilisateur
        Utilisateur acquereur = new Utilisateur();
        acquereur.setPseudo(rs.getString("id_utilisateur"));
        enchere.setAcquereur(acquereur);

        // Association pour l'article'
        ArticleAVendre articleAVendre = new ArticleAVendre();
        articleAVendre.setId(rs.getLong("no_article"));
        enchere.setArticleAVendre(articleAVendre);

        return enchere;
    }
}
