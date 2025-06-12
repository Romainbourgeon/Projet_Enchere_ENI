package org.example.super_projet_eni;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT pseudo,password,1  FROM MEMBRE where pseudo=?");// Prend le pseudo, mdp d'un compte actif (1) de la table membre et regarde si pseudo existe
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT u.pseudo, u.mot_de_passe, r.role FROM UTILISATEURS u inner join ROLES r ON r.IS_ADMIN = u.administrateur WHERE u.pseudo=?");
        return userDetailsManager;                      //Selectionne le pseudo,mdp,role de la table UTILISATEURS, il le lie avec ROLE, si est ADMIN  avec le pseudo alors admin
    }

    @Bean
    public PasswordEncoder passwordEncoder() {  //BCryptPasswordEncoder est une implémentation de l'interface PasswordEncoder qui applique l'algorithme BCrypt pour hacher les mots de passe.
        return new BCryptPasswordEncoder();   // BCrypt pour hacher les mots de passe
    }



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//GET est utilisé pour récupérer des données, afficher des informations sans modifier quoi que ce soit
// POST est utilisé pour soumettre des données qui vont modifier l'état du serveur (par exemple, s'authentifier via /login)

        http.authorizeHttpRequests(auth ->
        {
            auth.requestMatchers(HttpMethod.GET, "/").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/error").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/images/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/css/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/acceuil").permitAll();
            auth.requestMatchers(HttpMethod.POST,"/login").hasAnyRole("USER","ADMIN");
            auth.requestMatchers(HttpMethod.GET,"/monProfil").hasAnyRole("USER","ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/modifierProfil").hasAnyRole("USER","ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/deleteProfil").hasAnyRole("USER","ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/logout").hasAnyRole("USER","ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/vendreArticle").hasAnyRole("USER","ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/acheterArticle").hasAnyRole("USER","ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/deleteAccount").hasAnyRole("ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/desableAccount").hasAnyRole("ADMIN");
            auth.requestMatchers(HttpMethod.POST,"/gestionCatgArticle").hasAnyRole("ADMIN");



            auth.anyRequest().permitAll();//authenticated
        });

        //version de pages de login par defaut du framework (spring)
        http.formLogin(Customizer.withDefaults());  //Cette ligne active l'authentification par formulaire HTML (form login).
        http.csrf(c -> c.disable()); //Cela désactive la protection CSRF (Cross-Site Request Forgery).(API REST, tests, outils comme Postman)

        return http.build();
    }



}
