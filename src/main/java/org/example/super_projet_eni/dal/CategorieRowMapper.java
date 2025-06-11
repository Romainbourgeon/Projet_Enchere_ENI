package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Categorie;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategorieRowMapper implements RowMapper<Categorie> {
    @Override
    public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
        var categorie = new Categorie();
        categorie.setLibelle(rs.getString("libelle"));
        return categorie;
    }
}
