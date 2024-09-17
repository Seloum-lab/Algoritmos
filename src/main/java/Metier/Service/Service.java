/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Service;

import DAO.ClientDAO;
import DAO.JpaUtil;
import Metier.Modele.Client;
import com.google.maps.model.LatLng;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author DeLL
 */
public class Service {
    public Client authenticate(String mail, String password) {
        JpaUtil.creerContextePersistance();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ClientDAO clientDao = new ClientDAO();
        Client client = clientDao.findByMail(mail);
        JpaUtil.fermerContextePersistance();
        if (!passwordEncoder.encode(password).matches(client.getPassword())) {
            client = null;
        }
        return client;
    }
    
    
    public boolean signUp(String firstname, String lastname, String mail, String rawPassword, String phonenumber, String address) {
        boolean success = false;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword = passwordEncoder.encode(rawPassword);
        Client client = new Client(firstname, lastname, mail, phonenumber, hashPassword, address);
        LatLng coords = GeoNetApi.getLatLng(address);
        if (coords != null) {
            client.setLatitude(coords.lat);
            System.out.println(coords.lat);
            client.setLongitude(coords.lng);
            success = true;
        }
        ClientDAO clientDao = new ClientDAO();
        
        
        if (success) {
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                clientDao.create(client);
                JpaUtil.validerTransaction();
                success = true;
            } catch (Exception ex) {
                JpaUtil.annulerTransaction();
                success = false;

            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        return success;        
    }

    
    
}
