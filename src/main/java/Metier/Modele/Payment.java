/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

/**
 *
 * @author DeLL
 */
@Entity
public class Payment implements Serializable {
    
    public enum Method{
        CARD,
        CASH,
        PAYMENT,
        CHECK
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private Method paymentMethod;
    
    @OneToOne(mappedBy = "payment")
    private Appointment appointment;
    
    @Column(nullable = false)
    private boolean received;
    
    @Column(nullable = false)
    private boolean emmited;
    
    
    @PrePersist
    public void prePerist() {
        received = false;
        emmited = false;
    }

    public Payment() {
    }
    
    public Payment(Method paymentMethod, Appointment appointment) {
        this.paymentMethod = paymentMethod;
        this.appointment = appointment;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public boolean isEmmited() {
        return emmited;
    }

    public void setEmmited(boolean emmited) {
        this.emmited = emmited;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Method getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Method paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    
    
    
    
}
