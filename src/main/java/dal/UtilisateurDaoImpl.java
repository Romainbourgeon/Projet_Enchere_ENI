package dal;

import bo.Utilisateur;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class UtilisateurDaoImpl implements UtilisateurDao{

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UtilisateurDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Utilisateur utilisateur) {

    }

    @Override
    public Utilisateur read(String pseudo) {
        return null;
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
