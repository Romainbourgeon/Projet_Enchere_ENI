package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresseRowMapper implements RowMapper<Adresse> {
    @Override
    public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
        var adresse = new Adresse();
        adresse.setRue(rs.getString("rue"));
        adresse.setCodePostal(rs.getString("code_postal"));
        adresse.setVille(rs.getString("ville"));
        return adresse;
    }
}
