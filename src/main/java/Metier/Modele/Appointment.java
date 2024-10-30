/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Modele;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

/**
 *
 * @author DeLL
 */
@Entity
public class Appointment implements Serializable {
    public enum Status {
        PASSED,
        CANCELLED,
        WAITING
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    @Column(nullable = false)
    private Integer duration;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private Boolean paid;
    
    @Column(nullable = false)
    private Integer start;
    
    @ManyToOne(optional = false)
    private Client client;
    
    @ManyToOne(optional = false)
    private Publication publication;
    
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    
    
    
    @PrePersist
    public void prePersist() {
        if (duration == null) {
            duration = 2;
        }
        if (paid == null) {
            paid = false;
        }
    }
    
    public Appointment() {}

    public Appointment(Integer duration, LocalDate date, Integer start, Client client, Publication publication) {
        this.duration = duration;
        this.date = date;
        this.start = start;
        this.client = client;
        this.publication = publication;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
    
    
    
}
