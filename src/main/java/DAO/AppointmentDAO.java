/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.Appointment;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author DeLL
 */
public class AppointmentDAO {
    public void create(Appointment appointment) {
        JpaUtil.obtenirContextePersistance().persist(appointment);
    }
    
    public void delete(Appointment appointment) {
        JpaUtil.obtenirContextePersistance().remove(appointment);
    }
    
    public void update(Appointment appointment) {
        JpaUtil.obtenirContextePersistance().merge(appointment);
    }
    
    public Appointment findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Appointment.class, id);
    }
    
    public List<Appointment> getNextsAppointmentAsWorker(Long id) {
        List<Appointment> result;
        String query = "SELECT a FROM Appointment a WHERE a.status = :status and a.publication.client.id = :id order by a.dateAppointment";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("status", Appointment.Status.WAITING);
        tpQuery.setParameter("id", id);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
    
    
    public List<Appointment> getNextsAppointmentAsClient(Long id) {
        List<Appointment> result;
        String query = "SELECT a FROM Appointment a WHERE a.status = :status and a.client.id = :id ORDER BY a.dateAppointment, a.dateAppointment";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("status", Appointment.Status.WAITING);
        tpQuery.setParameter("id", id);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
    
    
    public List<Appointment> getToPayAppointment(Long id) {
        List<Appointment> result = null;
        String query = "SELECT a FROM Appointment WHERE a.status = :status and a.paid = :paid and a.publication.client.id = :id";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("id", id);
        tpQuery.setParameter("status", Appointment.Status.PASSED);
        tpQuery.setParameter("paid", false);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
    
    
    public List<Appointment> getPassedAppointmentsAsClient(Long id) {
        List<Appointment> result;
        String query = "SELECT a FROM Appointment a WHERE a.status = :status and a.client.id = :id ORDER BY a.dateAppointment, a.dateAppointment";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("status", Appointment.Status.PASSED);
        tpQuery.setParameter("id", id);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
    
    public List<Appointment> getPassedAppointmentsWorker(Long id) {
        List<Appointment> result;
        String query = "SELECT a FROM Appointment a WHERE a.status = :status and a.publication.client.id = :id order by a.dateAppointment";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("status", Appointment.Status.PASSED);
        tpQuery.setParameter("id", id);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
    
    
    public List<Appointment> getPassedAppointments() {
        List<Appointment> result;
        String query = "SELECT a FROM Appointment a WHERE a.status = :status order by a.dateAppointment";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("status", Appointment.Status.WAITING);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
    
    
    public List<Appointment> getCanceledAppointmentsAsWorker(Long id) {
        List<Appointment> result;
        String query = "SELECT a FROM Appointment a WHERE a.status = :status and a.publication.client.id = :id order by a.dateAppointment";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("status", Appointment.Status.CANCELLED);
        tpQuery.setParameter("id", id);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
    
    
    public List<Appointment> getCanceledAppointmentsAsClient(Long id) {
        List<Appointment> result;
        String query = "SELECT a FROM Appointment a WHERE a.status = :status and a.client.id = :id ORDER BY a.dateAppointment, a.dateAppointment";
        TypedQuery tpQuery = JpaUtil.obtenirContextePersistance().createQuery(query, Appointment.class);
        tpQuery.setParameter("status", Appointment.Status.CANCELLED);
        tpQuery.setParameter("id", id);
        
        result = (List<Appointment>) tpQuery.getResultList();
        
        return result;
    }
}
