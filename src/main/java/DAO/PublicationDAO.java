/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.Publication;
import java.util.List;
import javax.persistence.TypedQuery;

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
    
    
    public List<Publication> getListApproved(double distance) {
        List<Publication> result = null;
        String query = "SELECT p FROM Publication p WHERE p.status = :status order by p.date";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Publication.class);
        tpQuery.setParameter("status", Publication.Status.APPROVED);
        
        result = (List<Publication>) tpQuery.getResultList();
        
        
        return result;
    }
    
    public List<Publication> getListWaiting() {
        List<Publication> result = null;
        String query = "SELECT p FROM Publication p WHERE p.status = :status order by p.date";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Publication.class);
        tpQuery.setParameter("status", Publication.Status.WAITING);
        
        result = (List<Publication>) tpQuery.getResultList();
        
        
        return result;
    }
    
}
