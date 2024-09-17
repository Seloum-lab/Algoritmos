/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author DeLL
 */
@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    Integer pubication;
    Client client;
    
   //TODO add the @ for the date
    Date date;
    WorkType workType;

    public Publication() {
    }

    public Publication(Integer pubication, Client client, Date date, WorkType workType) {
        this.pubication = pubication;
        this.client = client;
        this.date = date;
        this.workType = workType;
    }

    public Integer getPubication() {
        return pubication;
    }

    public void setPubication(Integer pubication) {
        this.pubication = pubication;
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
