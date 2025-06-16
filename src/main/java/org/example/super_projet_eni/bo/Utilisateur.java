package org.example.super_projet_eni.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Utilisateur implements UserDetails {
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String motDePasse;
    private String confirmeMotDePasse;
    private int credit = 10;
    private boolean admin;
    private Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
    private Adresse adresse;
    private final List<ArticleAVendre> articleAVendreList= new ArrayList<>();

    public Utilisateur() {
    }


    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String motDePasse, String confirmeMotDePasse, int credit, boolean admin, Adresse adresse, Collection<? extends GrantedAuthority> authorities) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.confirmeMotDePasse = confirmeMotDePasse;
        this.credit = credit;
        this.admin = admin;
        this.adresse = adresse;
        this.authorities = authorities;
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
                ", confirmeMotDePasse='" + confirmeMotDePasse + '\'' +
                ", credit=" + credit +
                ", admin=" + admin +
                ", authorities=" + authorities +
                ", adresse=" + adresse +
                ", articleAVendreList=" + articleAVendreList +
                '}';
    }

    // constructeur test
    public Utilisateur(String user1, String password1, String mail) {
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

    public String getConfirmeMotDePasse() {
        return confirmeMotDePasse;
    }

    public void setConfirmeMotDePasse(String confirmeMotDePasse) {
        this.confirmeMotDePasse = confirmeMotDePasse;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
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

    public void setId(int idUtilisateur) {
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return pseudo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String encode) {
    }
}