/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.Appointment;

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
    
}
