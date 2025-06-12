package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Enchere;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnchereDaoImpl implements EnchereDao{

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
