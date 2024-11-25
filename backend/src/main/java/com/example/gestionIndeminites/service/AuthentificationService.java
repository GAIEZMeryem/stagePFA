package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.Services;
import com.example.gestionIndeminites.entity.Utilisateur;
import com.example.gestionIndeminites.repository.UtilisateurRepository;
import com.example.gestionIndiminites.util.SymmetricAES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthentificationService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private SymmetricAES aesUtil;

    public AuthentificationService() throws Exception {
        this.aesUtil = new SymmetricAES();
    }

    // Méthode pour enregistrer un utilisateur avec chiffrement du mot de passe
    public boolean register(String nomComplet, String login, String password, Services service, Utilisateur.Role role) {
        try {
            String encryptedPassword = aesUtil.encrypt(password);
            String aesKey = aesUtil.getEncodedKey();
            Utilisateur utilisateur = new Utilisateur(0, nomComplet, login, encryptedPassword, aesKey, service, role);
            utilisateurRepository.save(utilisateur);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }

    public boolean authenticate(String login, String password) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
            if (utilisateur != null) {
                
                System.out.println("Utilisateur trouvé pour le login : " + login);
                
                
                aesUtil.setEncodedKey(utilisateur.getAesKey());
                System.out.println("Clé AES chargée depuis la base de données : " + utilisateur.getAesKey());

                String decryptedPassword = aesUtil.decrypt(utilisateur.getPassword());
                System.out.println("Mot de passe déchiffré : " + decryptedPassword);

                
                if (decryptedPassword.equals(password)) {
                    System.out.println("Authentification réussie");
                    return true;
                } else {
                    System.out.println("Échec de l'authentification : mot de passe incorrect");
                }
            } else {
                System.out.println("Utilisateur non trouvé pour le login : " + login);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }

    // Méthode pour mettre à jour un utilisateur avec chiffrement du mot de passe
    public boolean updateUser(String nomComplet, String login, String password, Services service, Utilisateur.Role role) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
            if (utilisateur != null) {
                String encryptedPassword = aesUtil.encrypt(password);
                utilisateur.setNomComplet(nomComplet);
                utilisateur.setLogin(login);
                utilisateur.setPassword(encryptedPassword);
                utilisateur.setService(service);
                utilisateur.setRole(role);
                utilisateurRepository.save(utilisateur);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }

   
    public boolean deleteUser(String login) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
            if (utilisateur != null) {
                utilisateurRepository.delete(utilisateur);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }

    
    public Utilisateur assignRoleToUser(int id, Utilisateur.Role role) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
            if (utilisateur != null) {
                utilisateur.setRole(role);
                utilisateurRepository.save(utilisateur);
                return utilisateur;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace(); 
            return null;
        }
    }

    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUserByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }
}
