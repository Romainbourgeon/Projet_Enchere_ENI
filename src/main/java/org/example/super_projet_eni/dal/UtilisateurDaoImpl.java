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
public class UtilisateurDaoImpl implements UtilisateurDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private AdresseDao adresseDao;

    public UtilisateurDaoImpl(NamedParameterJdbcTemplate jdbcTemplate,  AdresseDao adresseDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.adresseDao = adresseDao;
    }

    // requêtes SQL
    private final String SELECT_BY_PSEUDO = "select * from UTILISATEURS where pseudo = :pseudo";
    private final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
    private final String INSERT = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) "
            + " VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :credit, :administrateur, :no_adresse)";
    private final String UPDATE = "UPDATE UTILISATEURS SET no_adresse = :no_adresse, telephone = :telephone, email = :email WHERE pseudo = :pseudo";
    private final String DELETE = "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo";

    @Override
    public Utilisateur create(Utilisateur utilisateur) {
        if (utilisateur.getAdresse() != null && utilisateur.getAdresse().getId() == 0) {
            long idAdresse = adresseDao.create(utilisateur.getAdresse());
            utilisateur.getAdresse().setId(idAdresse);
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", utilisateur.getPseudo());
        params.addValue("nom", utilisateur.getNom());
        params.addValue("prenom", utilisateur.getPrenom());
        params.addValue("email", utilisateur.getEmail());
        params.addValue("telephone", utilisateur.getTelephone());
        params.addValue("motDePasse", utilisateur.getMotDePasse());
        params.addValue("credit", utilisateur.getCredit());
        params.addValue("administrateur", utilisateur.isAdmin());
        params.addValue("no_adresse", utilisateur.getAdresse() != null ? utilisateur.getAdresse().getId() : null);

        jdbcTemplate.update(
                INSERT,
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
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Utilisateur> readAll() {
        return jdbcTemplate.query(SELECT_ALL, new UtilisateurRowMapper());
    }

    /*@Override
    public void update(Utilisateur utilisateur) {
        var parameters = new MapSqlParameterSource();
        parameters.addValue("no_adresse", utilisateur.getAdresse() != null ? utilisateur.getAdresse().getId() : null);
        parameters.addValue("telephone", utilisateur.getTelephone());
        parameters.addValue("email", utilisateur.getEmail());
        parameters.addValue("pseudo", utilisateur.getPseudo());
        jdbcTemplate.update(UPDATE, parameters);
    }*/

    @Override
    public void delete(String pseudo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);
        jdbcTemplate.update(DELETE, params);
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


    @Override
    public void update(Utilisateur utilisateur) {
        Adresse adresse = utilisateur.getAdresse();

        // 1. Mise à jour ou création de l'adresse
        if (adresse != null) {
            if (adresse.getId() != 0) {
                // L'adresse existe déjà => on la met à jour
                adresseDao.update(adresse);
            } else {
                // Nouvelle adresse => on la crée et récupère l'id généré
                long idAdresse = adresseDao.create(adresse);
                adresse.setId(idAdresse);
            }
        }

        // 2. Mise à jour des infos utilisateur en liant la bonne adresse
        var parameters = new MapSqlParameterSource();
        parameters.addValue("no_adresse", adresse != null ? adresse.getId() : null);
        parameters.addValue("telephone", utilisateur.getTelephone());
        parameters.addValue("email", utilisateur.getEmail());
        parameters.addValue("pseudo", utilisateur.getPseudo());

        jdbcTemplate.update(UPDATE, parameters);
    }


}
