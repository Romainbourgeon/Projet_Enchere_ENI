package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdresseDaoImpl implements AdresseDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public AdresseDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Requêtes SQL
    private final String SELECT_ALL = "SELECT * FROM ADRESSES";
    private final String SELECT_BY_ID = "SELECT * FROM ADRESSES WHERE id = :id";
    private final String INSERT = "INSERT INTO ADRESSES (rue, code_postal, ville) VALUES (:rue, :code_postal, :ville)";
    private final String UPDATE = "UPDATE ADRESSES SET rue = :rue, code_postal = :code_postal, ville = :ville WHERE id = :id";
    final static String DELETE = "delete from ADRESSES where id=:id";


    @Override
    public long create(Adresse adresse) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("rue", adresse.getRue());
        namedParameters.addValue("code_postal", adresse.getCodePostal());
        namedParameters.addValue("ville", adresse.getVille());
        jdbcTemplate.update(INSERT, namedParameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Adresse read(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new AdresseRowMapper());
    }

    @Override
    public List<Adresse> readAll() {
        return jdbcTemplate.query(SELECT_ALL, new AdresseRowMapper());
    }

    @Override
    public void update(Adresse adresse) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("rue", adresse.getRue());
        namedParameters.addValue("code_postal", adresse.getCodePostal());
        namedParameters.addValue("ville", adresse.getVille());
        namedParameters.addValue("id", adresse.getId());
        jdbcTemplate.update(UPDATE, namedParameters);
    }

    @Override
    public void delete(long id) {
        var namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        jdbcTemplate.update(DELETE, namedParameters);
    }
}
