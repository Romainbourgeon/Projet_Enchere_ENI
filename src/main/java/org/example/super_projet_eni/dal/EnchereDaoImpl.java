package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Enchere;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnchereDaoImpl implements EnchereDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public EnchereDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Requêtes SQL
    private final String SELECT_ALL = "SELECT * FROM ENCHERES";
    private final String SELECT_BY_USER_AND_ARTICLE_AND_PRICE = "SELECT * FROM ENCHERES WHERE (id_utilisateur = :id_utilisateur " +
            "AND no_article = :no_article AND montant_enchere = :montant_enchere)";
    private final String INSERT = "INSERT INTO ENCHERES (id_utilisateur, no_article, montant_enchere, date_enchere)" +
            " VALUES (:id_utilisateur, :no_article, :montant_enchere, :date_enchere)";
    final static String DELETE = "delete from ENCHERES WHERE (id_utilisateur = :id_utilisateur " +
            "AND no_article = :no_article AND montant_enchere = :montant_enchere)";



    @Override
    public void create(Enchere enchere, Utilisateur acquereur, ArticleAVendre articleAVendre) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", acquereur.getPseudo());
        namedParameters.addValue("no_article", articleAVendre.getId());
        namedParameters.addValue("montant_enchere", enchere.getMontant());
        namedParameters.addValue("date_enchere", enchere.getDate());
        jdbcTemplate.update(INSERT, namedParameters);
    }

    @Override
    public Enchere read(ArticleAVendre articleAVendre) {
        return null;
    }

    @Override
    public List<Enchere> readAll() {
        return List.of();
    }

    @Override
    public void delete(ArticleAVendre articleAVendre) {

    }
}
