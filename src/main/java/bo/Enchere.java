package bo;

import java.time.LocalDateTime;

public class Enchere  {
   private LocalDateTime date;
    private  int montant;
    private Utilisateur acquereur;
    private ArticleAVendre articleAVendre;


    public Enchere() {
    }

    public Enchere(LocalDateTime date, int montant, Utilisateur acquereur, ArticleAVendre articleAVendre) {
        this.date = date;
        this.montant = montant;
        this.acquereur = acquereur;
        this.articleAVendre = articleAVendre;
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "date=" + date +
                ", montant=" + montant +
                ", acquereur=" + acquereur +
                ", articleAVendre=" + articleAVendre +
                '}';
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Utilisateur getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(Utilisateur acquereur) {
        this.acquereur = acquereur;
    }

    public ArticleAVendre getArticleAVendre() {
        return articleAVendre;
    }

    public void setArticleAVendre(ArticleAVendre articleAVendre) {
        this.articleAVendre = articleAVendre;
    }
}
