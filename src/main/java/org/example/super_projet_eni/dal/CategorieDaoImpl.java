package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Categorie;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieDaoImpl implements CategorieDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    public CategorieDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Requêtes SQL
    private final String SELECT_ALL = "SELECT * FROM CATEGORIES";
    private final String SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie = :no_categorie";
    private final String INSERT = "INSERT INTO CATEGORIES (libelle) VALUES (:libelle)";
    private final String UPDATE = "UPDATE CATEGORIES SET libelle = :libelle WHERE id = :id";
    final static String DELETE = "delete from CATEGORIES where id=:id";

    @Override
    public long create(Categorie categorie) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("libelle", categorie.getLibelle());
        jdbcTemplate.update(INSERT, namedParameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Categorie read(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new CategorieRowMapper());
    }

    @Override
    public List<Categorie> readAll() {
        return jdbcTemplate.query(SELECT_ALL, new CategorieRowMapper());
    }

    @Override
    public void update(Categorie categorie) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("libelle", categorie.getLibelle());
        namedParameters.addValue("id", categorie.getId());
        jdbcTemplate.update(UPDATE, namedParameters);
    }

    @Override
    public void delete(long id) {
        var namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        jdbcTemplate.update(DELETE, namedParameters);
    }
}
