package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Categorie;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleAVendreDaoImpl implements ArticleAVendreDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ArticleAVendreDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Requêtes SQL
    private final String SELECT_BY_ID = "SELECT * FROM ARTICLES_A_VENDRE WHERE no_article = :no_article";
    private final String SELECT_ALL = "SELECT * FROM ARTICLES_A_VENDRE";
    private final String SELECT_ALL_WITH_ENCHERES_ACTIVES = "SELECT * FROM ARTICLES_A_VENDRE WHERE statut_enchere = 1";
    private final String SELECT_ALL_WITH_ENCHERES_ACTIVES_BY_CATEGORIE = "SELECT * FROM ARTICLES_A_VENDRE WHERE ((GETDATE() BETWEEN date_debut_encheres AND date_fin_encheres) AND (no_categorie = :no_categorie))";
    private final String SELECT_ALL_BY_UTILISATEUR = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = :id_utilisateur";
    private final String INSERT = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, photo, date_debut_encheres, date_fin_encheres, " +
            "statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait)" +
            " VALUES (:nom_article, :description, :photo, :date_debut_encheres, :date_fin_encheres, " +
            ":statut_enchere, :prix_initial, :prix_vente, :id_utilisateur, :no_categorie, :no_adresse_retrait)";


    @Override
    public long create(ArticleAVendre articleAVendre, Utilisateur vendeur, Categorie categorie) {
        var keyholder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("nom_article", articleAVendre.getNom());
        namedParameters.addValue("description", articleAVendre.getDescription());
        namedParameters.addValue("photo", articleAVendre.getPhoto());
        namedParameters.addValue("date_debut_encheres", articleAVendre.getDateDebutEncheres());
        namedParameters.addValue("date_fin_encheres", articleAVendre.getDateFinEncheres());
        namedParameters.addValue("statut_enchere", articleAVendre.getStatut());
        namedParameters.addValue("prix_initial", articleAVendre.getPrixInitial());
        namedParameters.addValue("prix_vente", articleAVendre.getPrixVente());
        namedParameters.addValue("id_utilisateur", vendeur.getPseudo());
        namedParameters.addValue("no_categorie", categorie.getId());
        namedParameters.addValue("no_adresse_retrait", vendeur.getAdresse());

        jdbcTemplate.update(INSERT, namedParameters, keyholder);

        return keyholder.getKey().longValue();
    }

    @Override
    public ArticleAVendre read(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_article", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new ArticleAVendreRowMapper());
    }

    @Override
    public List<ArticleAVendre> readAll() {
        return jdbcTemplate.query(SELECT_ALL, new ArticleAVendreRowMapper());
    }

    @Override
    public List<ArticleAVendre> readAllEncheresActives() {
        return jdbcTemplate.query(SELECT_ALL_WITH_ENCHERES_ACTIVES, new ArticleAVendreRowMapper());
    }

    @Override
    public List<ArticleAVendre> readAllByUtilisateur(Utilisateur utilisateur) {
        return jdbcTemplate.query(SELECT_ALL_BY_UTILISATEUR, new ArticleAVendreRowMapper());
    }

    @Override
    public List<ArticleAVendre> readAllEncheresActivesByCategorie(Categorie categorie) {
        return jdbcTemplate.query(SELECT_ALL_WITH_ENCHERES_ACTIVES_BY_CATEGORIE, new ArticleAVendreRowMapper());
    }
}
