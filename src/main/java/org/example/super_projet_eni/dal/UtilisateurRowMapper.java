package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurRowMapper implements RowMapper<Utilisateur> {


    @Override
    public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
        var utilisateur = new Utilisateur();
        utilisateur.setPseudo(rs.getString("pseudo"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setTelephone(rs.getString("telephone"));
        utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
        utilisateur.setCredit(rs.getInt("credit"));
        utilisateur.setAdmin(rs.getBoolean("administrateur"));
        return utilisateur;
    }
}
