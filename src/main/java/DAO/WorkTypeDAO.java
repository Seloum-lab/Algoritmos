/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.WorkType;
import java.util.List;
import javax.persistence.TypedQuery;

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
    
    public WorkType findById(String workType) {
        return JpaUtil.obtenirContextePersistance().find(WorkType.class, workType);
    }
    
    public List<WorkType> getList() {
        List<WorkType> result = null;
        String query = "SELECT w FROM WorkType w order by w.worktype";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, WorkType.class);
        
        result = (List<WorkType>) tpQuery.getResultList();
        
        return result;
    }
    
}
