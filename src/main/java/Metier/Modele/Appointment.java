/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Modele;

import Converters.PairConverter;
import Converters.SetIntegerConverter;
import Utils.Pair;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

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
    
    @ElementCollection
    @Column(nullable = false, columnDefinition = "VARCHAR(350)")
    @Converter(name = "SetConverter", converterClass = SetIntegerConverter.class)
    @Convert("SetConverter")
    private Map<Integer, Set<Integer>> duration;
    
    
    @Column(nullable = false)
    @Converter(name = "PairConverter", converterClass = PairConverter.class)
    @Convert("PairConverter")
    private Pair<Integer, Integer> dateAppointment;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
        
    @ManyToOne(optional = false)
    private Client client;
    
    @ManyToOne(optional = false)
    private Publication publication;
    
    @Column(nullable = false)
    private int price;
    
    private Integer note;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @PrePersist
    public void prePersist() {
        int result = 0;
        for (Set<Integer> set : duration.values()) {
            result += set.size()*publication.getPrice();
        }
        price = result;
        status = Status.WAITING;
        date = new Date();
    }

    public Integer getNote() {
        return note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    
    
    public void setNote(Integer note) {
        this.note = note;
    }

    
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    
    
    
    

    
    public Appointment() {}

    public Appointment(LocalDate dateAppointment, Client client, Publication publication, Map<Integer,Set<Integer>> duration) {
        int year = dateAppointment.getYear();
        int weekOfYear = dateAppointment.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        this.dateAppointment = new Pair(year, weekOfYear);
        this.client = client;
        this.publication = publication;
        this.duration = duration;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Integer, Set<Integer>> getDuration() {
        return this.duration;
    }
    
   public Pair<Integer, Integer> getDateAppointment() {
       return this.dateAppointment;
   }
    
    public Date getDate() {
        return this.date;
    }

    public void setDate(LocalDate dateAppointment) {
        int year = dateAppointment.getYear();
        int weekOfYear = dateAppointment.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        this.dateAppointment = new Pair(year, weekOfYear);
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
