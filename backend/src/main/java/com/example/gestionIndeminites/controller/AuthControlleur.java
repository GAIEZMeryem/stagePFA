package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.DTO.RegisterDTO;
import com.example.gestionIndeminites.entity.LoginResponse;
import com.example.gestionIndeminites.entity.Utilisateur;
import com.example.gestionIndeminites.service.AuthentificationService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")

public class AuthControlleur {

    @Autowired
    private AuthentificationService authenticationService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterDTO registerDTO, HttpSession session) {
        try {
            System.out.println("Tentative de connexion avec le login : " + registerDTO.getLogin());
            boolean authenticated = authenticationService.authenticate(registerDTO.getLogin(), registerDTO.getPassword());
            
            if (authenticated) {
                Utilisateur utilisateur = authenticationService.getUserByLogin(registerDTO.getLogin());
                session.setAttribute("utilisateur", utilisateur);
                return ResponseEntity.ok(new LoginResponse("Login successful", utilisateur.getRole().name(), utilisateur.getNomComplet()));
            } else {
                System.out.println("Échec de l'authentification");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
            }
        } catch (Exception e) {
            System.out.println("Erreur d'authentification : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @CrossOrigin
    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO registerDTO) {
        try {
            boolean success = authenticationService.register(
                    registerDTO.getNomComplet(),
                    registerDTO.getLogin(),
                    registerDTO.getPassword(),
                    registerDTO.getService(),
                    registerDTO.getRole()
            );
            return success ? "Registration successful" : "Registration failed";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @CrossOrigin
    @PutMapping("/update")
    public String update(@RequestBody RegisterDTO registerDTO) {
        try {
            boolean updated = authenticationService.updateUser(
                    registerDTO.getNomComplet(),
                    registerDTO.getLogin(),
                    registerDTO.getPassword(),
                    registerDTO.getService(),
                    registerDTO.getRole()
            );
            return updated ? "Update successful" : "Update failed";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @CrossOrigin
    @PutMapping("/assignRole/{id}")
    public String assignRole(@PathVariable int id, @RequestBody String role) {
        try {
            Utilisateur.Role utilisateurRole = Utilisateur.Role.valueOf(role.toUpperCase());
            Utilisateur updatedUser = authenticationService.assignRoleToUser(id, utilisateurRole);
            return updatedUser != null ? "Role assigned successfully" : "User not found";
        } catch (IllegalArgumentException e) {
            return "Invalid role: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @CrossOrigin
    @DeleteMapping("/delete/{login}")
    public String delete(@PathVariable String login) {
        try {
            boolean deleted = authenticationService.deleteUser(login);
            return deleted ? "Deletion successful" : "Deletion failed";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @CrossOrigin
    @GetMapping("/users")
    public List<Utilisateur> getAllUsers() {
        try {
            return authenticationService.getAllUsers();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching users: " + e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    }

    @CrossOrigin
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
