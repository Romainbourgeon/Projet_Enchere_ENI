package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UtilisateurDaoImpl implements UtilisateurDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UtilisateurDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

// requêtes SQL
    private final String SELECT_BY_PSEUDO = "select * from UTILISATEURS where pseudo = :pseudo";
    private final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
    private final String INSERT = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) "
            + " VALUES (:pseudo, :nom, :prenom, :email, :telephone, :mot_de_passe, :credit, :administrateur, :no_adresse)";

//    private final String FIND_TITRE = "SELECT TITRE FROM FILM WHERE  id = :id";



    @Override
    public Utilisateur create(Utilisateur utilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", utilisateur.getPseudo());
        params.addValue("nom", utilisateur.getNom());
        params.addValue("prenom", utilisateur.getPrenom());
        params.addValue("email", utilisateur.getEmail());
        params.addValue("telephone", utilisateur.getTelephone());
        params.addValue("motDePasse", utilisateur.getMotDePasse());
        params.addValue("credit", utilisateur.getCredit());
        params.addValue("administrateur", utilisateur.isAdmin());
        params.addValue("noAdresse", utilisateur.getAdresse() != null ? utilisateur.getAdresse().getId() : null);

        jdbcTemplate.update(
                "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) " +
                        "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :credit, :administrateur, :noAdresse)",
                params);

        var paramsRole = new MapSqlParameterSource();
        paramsRole.addValue("pseudo", utilisateur.getPseudo());
        paramsRole.addValue("role", "ROLE_USER");
        paramsRole.addValue("isAdmin", 0);

        jdbcTemplate.update(
                "INSERT INTO UTILISATEUR_ROLES (pseudo, role, is_admin) VALUES (:pseudo, :role, :isAdmin)",
                paramsRole
        );

        return utilisateur;
    }


    @Override
    public Utilisateur read(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);
        return jdbcTemplate.queryForObject(SELECT_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
    }

    @Override
    public List<Utilisateur> readAll() {
        return jdbcTemplate.query(SELECT_ALL, new UtilisateurRowMapper());
    }

    @Override
    public void update(Utilisateur utilisateur) {

    }

    @Override
    public void delete(String pseudo) {

    }

    @Override
    public Optional<Utilisateur> voirUtilisateurByPseudo(String pseudo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);
        try {
            Utilisateur utilisateur = jdbcTemplate.queryForObject(
                    SELECT_BY_PSEUDO,
                    params,
                    new UtilisateurRowMapper()
            );
            return Optional.ofNullable(utilisateur);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
