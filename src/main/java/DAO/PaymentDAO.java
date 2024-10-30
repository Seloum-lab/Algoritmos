/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Modele.Payment;

/**
 *
 * @author DeLL
 */
public class PaymentDAO {
     public void create(Payment payment) {
        JpaUtil.obtenirContextePersistance().persist(payment);
    }
    
    public void delete(Payment payment) {
        JpaUtil.obtenirContextePersistance().remove(payment);
    }
    
    public void update(Payment payment) {
        JpaUtil.obtenirContextePersistance().merge(payment);
    }
    
    public Payment findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Payment.class, id);
    }
    
}
