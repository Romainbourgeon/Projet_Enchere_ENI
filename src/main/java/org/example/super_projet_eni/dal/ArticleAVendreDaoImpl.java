package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.ArticleAVendre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleAVendreDaoImpl implements ArticleAVendreDao{

    @Override
    public long create(ArticleAVendre articleAVendre) {
        return 0;
    }

    @Override
    public ArticleAVendre read(long id) {
        return null;
    }

    @Override
    public List<ArticleAVendre> readAll() {
        return List.of();
    }

    @Override
    public void update(ArticleAVendre articleAVendre) {

    }

    @Override
    public void delete(long id) {

    }

}
