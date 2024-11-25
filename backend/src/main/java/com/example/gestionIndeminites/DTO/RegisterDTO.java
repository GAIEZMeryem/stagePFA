package com.example.gestionIndeminites.DTO;

import com.example.gestionIndeminites.entity.Services;
import com.example.gestionIndeminites.entity.Utilisateur.Role;

public class RegisterDTO {
    private String nomComplet;
    private String login;
    private String password;
    private Services service;
    private Role role; 

   

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
