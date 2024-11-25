package com.example.gestionIndeminites.entity;

import jakarta.persistence.*;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomComplet;
    private String login;
    private String password;
    private String aesKey;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN,
        SUPER_ADMIN,
        MODERATOR,
        USER
    }

    

    public Utilisateur() {
        super();
    }

    public Utilisateur(int id, String nomComplet, String login, String password, String aesKey, Services service, Role role) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.login = login;
        this.password = password;
        this.aesKey = aesKey;
        this.service = service;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
