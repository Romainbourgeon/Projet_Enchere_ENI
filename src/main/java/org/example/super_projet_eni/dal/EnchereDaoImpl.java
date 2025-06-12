package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Enchere;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnchereDaoImpl implements EnchereDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    public EnchereDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Requêtes SQL
    private final String SELECT_ALL = "SELECT * FROM ENCHERES";
    private final String SELECT_BY_ID = "SELECT * FROM ENCHERES WHERE id = :id";
    private final String INSERT = "INSERT INTO ENCHERES (id_utilisateur, no_article, montant_enchere, date_enchere)" +
            " VALUES (:id_utilisateur, :no_article, :montant_enchere, :date_enchere)";
    private final String UPDATE = "UPDATE ENCHERES SET id_utilisateur = :id_utilisateur, " +
            "no_article = :no_article, montant_enchere = :montant_enchere, date_enchere = :date_enchere, WHERE id = :id";
    final static String DELETE = "delete from ENCHERES where id=:id";


    @Override
    public long create(Enchere enchere) {
        return 0;
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
    public void update(Enchere enchere) {

    }

    @Override
    public void delete(ArticleAVendre articleAVendre) {

    }
}
