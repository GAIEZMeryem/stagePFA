package com.example.gestionIndeminites.entity;

public class LoginResponse {
    private String message;
    private String role;
    private String nomComplet;

    public LoginResponse(String message, String role, String nomComplet) {
        this.message = message;
        this.role = role;
        this.nomComplet = nomComplet;
    }

   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
}
