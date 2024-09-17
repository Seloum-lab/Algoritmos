/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.Client;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author DeLL
 */
public class ClientDAO {
    public void create(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    public void delete(Client client) {
        JpaUtil.obtenirContextePersistance().remove(client);
    }
    
    public void update(Client client) {
        JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    public Client findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
    public Client findByMail(String mail) {
        Client res = null; //On intialise la valeur de retour
        try {
        String query = "SELECT c FROM Client c WHERE c.mail = :mail"; 
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Client.class); 
        tpQuery.setParameter("mail", mail); 
        
        res = (Client) tpQuery.getSingleResult(); // retourne le Client trouve 
        } catch (NoResultException ex) { //Si il n'y a pas de résultat
            res = null;            
        }
        return res;
    }
    
    
    
}
