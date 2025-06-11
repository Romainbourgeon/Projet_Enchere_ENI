package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UtilisateurDaoImpl implements UtilisateurDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UtilisateurDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // requêtes SQL
    private final String SELECT_BY_PSEUDO = "select * from UTILISATEURS where pseudo = :pseudo";


    @Override
    public void create(Utilisateur utilisateur) {

    }

    @Override
    public Utilisateur read(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);
        return jdbcTemplate.queryForObject(SELECT_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
    }

    @Override
    public List<Utilisateur> readAll() {
        return null;
    }

    @Override
    public void update(Utilisateur utilisateur) {

    }

    @Override
    public void delete(String pseudo) {

    }




}
