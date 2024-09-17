/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.WorkType;

/**
 *
 * @author DeLL
 */
public class WorkTypeDAO {
    
    public void create(WorkType workType) {
        JpaUtil.obtenirContextePersistance().persist(workType);
    }
    
    public void delete(WorkType workType) {
        JpaUtil.obtenirContextePersistance().remove(workType);
    }
    
    public void update(WorkType workType) {
        JpaUtil.obtenirContextePersistance().merge(workType);
    }
    
    public WorkType findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(WorkType.class, id);
    }
    
}
