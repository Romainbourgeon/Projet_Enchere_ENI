package org.example.super_projet_eni.bo;

import java.util.ArrayList;
import java.util.List;


public class Utilisateur  {
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String motDePasse;
    private int credit;
    private boolean admin;
    private Adresse adresse;
    private final List<ArticleAVendre> articleAVendreList= new ArrayList<>();


    public Utilisateur() {
    }

    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String motDePasse, int credit, boolean admin, Adresse adresse) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.admin = admin;
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", credit=" + credit +
                ", admin=" + admin +
                ", adresse=" + adresse +
                ", articleAVendreList=" + articleAVendreList +
                '}';
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<ArticleAVendre> getArticleAVendreList() {
        return articleAVendreList;
    }
}
