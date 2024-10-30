/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

/**
 *
 * @author DeLL
 */
@Entity
public class Publication implements Serializable {
    public enum Status {
        WAITING,
        APPROVED,
        REJECTED
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    private String title;
    private String description;
    
    
    @Column(nullable=false)
    private Integer numberNotes;
    private Float average;
    
    @ManyToOne(optional = false)
    private Client client;
    
   //TODO add the @ for the date
    @Column(nullable = false)
    private Date date;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private WorkType workType;
    
    
    @Column(nullable = false)
    private Integer price;
    
    private Status status;
    
    private Double distanceMax;
    
    @PrePersist
    public void prePersist() {
        if (price == null) {
            price = 20;
        }
        if (numberNotes == null) {
            numberNotes = 0;
        }
    }

    public Publication() {
    }

    public Publication(Date date, WorkType workType, int price, String title, String description) {
        this.date = date;
        this.workType = workType;
        this.price = price;
        this.status = Status.WAITING;
        this.title = title;
        this.description = description;
    }

    public Double getDistanceMax() {
        return distanceMax;
    }

    public void setDistanceMax(Double distanceMax) {
        this.distanceMax = distanceMax;
    }
    
    

    public Integer getNumberNotes() {
        return numberNotes;
    }

    public void setNumberNotes(Integer numberNotes) {
        this.numberNotes = numberNotes;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    
    
    public Long getPubication() {
        return id;
    }

    public void setPubication(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }
    

    
    
    
}
