/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.Publication;

/**
 *
 * @author DeLL
 */
public class PublicationDAO {
    public void create(Publication publication) {
        JpaUtil.obtenirContextePersistance().persist(publication);
    }
    
    public void delete(Publication publication) {
        JpaUtil.obtenirContextePersistance().remove(publication);
    }
    
    public void update(Publication publication) {
        JpaUtil.obtenirContextePersistance().merge(publication);
    }
    
    public Publication findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Publication.class, id);
    }
    
}
