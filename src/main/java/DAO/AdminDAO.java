/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.Admin;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author DeLL
 */

public class AdminDAO {
    public void create(Admin admin) {
        JpaUtil.obtenirContextePersistance().persist(admin);
    }
    
    public void delete(Admin admin) {
        JpaUtil.obtenirContextePersistance().remove(admin);
    }
    
    public void update(Admin admin) {
        JpaUtil.obtenirContextePersistance().merge(admin);
    }
    
    public Admin findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Admin.class, id);
    }
    
    public Admin findByMail(String mail) {
        Admin res = null; //On intialise la valeur de retour
        try {
        String query = "SELECT c FROM Admin c WHERE c.mail = :mail"; 
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Admin.class); 
        tpQuery.setParameter("mail", mail); 
        
        res = (Admin) tpQuery.getSingleResult(); // retourne le Admin trouve 
        } catch (NoResultException ex) { //Si il n'y a pas de résultat
            res = null;            
        }
        return res;
    }
    
}
