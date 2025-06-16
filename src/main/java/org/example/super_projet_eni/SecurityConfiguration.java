package org.example.super_projet_eni;


import org.example.super_projet_eni.bll.UtilisateurService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UtilisateurService utilisateurService;

    public SecurityConfiguration(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UtilisateurService utilisateurService) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(utilisateurService) // ici utilisateurService implémente UserDetailsService
                .passwordEncoder(passwordEncoder());
        return auth.build();
    }

    // A supprimer car authenticationManager gère déjà l'authentification
    /*@Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT pseudo,password,1 FROM MEMBRE where pseudo=?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT role FROM utilisateur_roles WHERE pseudo=?");
        return userDetailsManager;
    }*/


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
                    auth.requestMatchers(HttpMethod.GET, "/accueil").authenticated();
                    auth.requestMatchers("/register").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/connexion").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/connexion").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/monProfil").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/modifierProfil").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/deleteProfil").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/logout").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/vendreArticle").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/acheterArticle").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/deleteAccount").hasAnyRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/desableAccount").hasAnyRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/gestionCatgArticle").hasAnyRole("ADMIN");


                    auth.anyRequest().authenticated();
                })

                //version de pages de login par defaut du framework (spring)
                /*http.formLogin(Customizer.withDefaults());  //Cette ligne active l'authentification par formulaire HTML (form login).
                http.csrf(c -> c.disable()); //Cela désactive la protection CSRF (Cross-Site Request Forgery).(API REST, tests, outils comme Postman)*/

                .csrf(Customizer.withDefaults())
                .cors(Customizer.withDefaults())

                // Permets d'utiliser notre page de connexion et de la lier à spring security
                .formLogin(f ->
                        f.loginPage("/connexion")
                                .loginProcessingUrl("/connexion")
                                .permitAll()
                );


        return http.build();
    }
}