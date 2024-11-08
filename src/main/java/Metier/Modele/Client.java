/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author DeLL
 */
@Entity
public class Client implements Serializable {
    public enum Status {
        FREE,
        TAKEN,
        NOT_FREE
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Publication> publications;
    
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String mail;
    private String phoneNumber;
    private String password;
    private Double latitude;
    private Double longitude;
    private String address;
    private boolean[][] clienDisponibilities;
    private HashMap<LocalDate, Status[][]>actualDisponibilities;
    
    
    public void addDisponibility(LocalDate date, Status[][] disponibiliy) {
        this.actualDisponibilities.put(date, disponibiliy);
    }
    
    public void addDisponibility(LocalDate date) {
        Status disponibility[][] = new Status[7][14];
        for (int day = 0; day<7; day++) {
            for (int hour = 0; hour<12; hour++) {
                if (this.clienDisponibilities[day][hour])
                    disponibility[day][hour] = Status.FREE;
                else disponibility[day][hour] = Status.NOT_FREE;
            }
        }
        this.actualDisponibilities.put(date, disponibility);
    }
    
    public void addPublication(Publication publication) {
        this.publications.add(publication);
    }

    public Client(String firstName, String lastName, String mail, String phoneNumber, String password, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.clienDisponibilities = new boolean[7][12];
        this.actualDisponibilities = new HashMap<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean[][] getClientDisponibilities() {
        return clienDisponibilities;
    }

    public void setClientDisponibilities(boolean[][] clienDisponibilities) {
        this.clienDisponibilities = clienDisponibilities;
    }

    public HashMap<LocalDate, Status[][]> getActualDisponibilities() {
        return actualDisponibilities;
    }

    public void setActualDisponibilities(HashMap<LocalDate, Status[][]> actualDisponibilities) {
        this.actualDisponibilities = actualDisponibilities;
    }
    
    
    
    
    public Client() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    
    
    
}
