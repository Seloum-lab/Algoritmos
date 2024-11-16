/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Modele;

import Converters.BooleanDoubleTableConverter;
import Converters.PairConverter;
import Converters.StatusDoubleTableConverter;
import Utils.Pair;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Map;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import org.eclipse.persistence.annotations.MapKeyConvert;
import org.eclipse.persistence.annotations.Converter;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.indirection.IndirectMap;

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
    
    @Column(unique = true)
    private String phoneNumber;
    
    private String password;
    private Double latitude;
    private Double longitude;
    private String address;
    
    @Basic
    @Converter(name = "BoolTableConverter", converterClass = BooleanDoubleTableConverter.class)
    @Convert("BoolTableConverter")
    @Column(columnDefinition = "VARCHAR(255)", name = "Dispo")
    private boolean[][] clientDisponibilities;
    
    @ElementCollection
    @CollectionTable(name = "actual_disponibilities", joinColumns = @JoinColumn(name = "client_id"))
    @MapKeyColumn(name = "date")
    @Column(name = "disponibility", columnDefinition = "VARCHAR (1000)")
    @Converter(name="PairConverter", converterClass = PairConverter.class)
    @MapKeyConvert("PairConverter")
    @Converter(name = "StatusTableConverter", converterClass = StatusDoubleTableConverter.class)
    @Convert("StatusTableConverter")
    private Map<Pair<Integer, Integer>, Status[][]>actualDisponibilities;
    
    
    public Status[][] addDisponibility(LocalDate date, Status[][] disponibility) {
        if (this.clientDisponibilities == null) {
            return null;
        }
        int year = date.getYear();
        int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        this.actualDisponibilities.put(new Pair(year, weekOfYear), disponibility);
        return disponibility;
    }
    
    public Status[][] addDisponibility(LocalDate date) throws Exception{
        if (this.clientDisponibilities == null) {
            return null;
        }
        Status disponibility[][] = new Status[7][12];
        for (int day = 0; day<7; day++) {
            for (int hour = 0; hour<12; hour++) {
                if (this.clientDisponibilities[day][hour])
                    disponibility[day][hour] = Status.FREE;
                else disponibility[day][hour] = Status.NOT_FREE;
            }
        }
        int year = date.getYear();
        int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        this.actualDisponibilities.put(new Pair(year, weekOfYear), disponibility);
        return disponibility;
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
        this.actualDisponibilities = new IndirectMap<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean[][] getClientDisponibilities() {
        return clientDisponibilities;
    }

    public void setClientDisponibilities(boolean[][] clientDisponibilities) {
        this.clientDisponibilities = clientDisponibilities;
    }
    
    public Status[][] getActualDisponibilities (LocalDate date){
        int year = date.getYear();
        int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        return this.actualDisponibilities.get(new Pair(year, weekOfYear));
    }
    
    public Map<Pair<Integer, Integer>, Status[][]> getActualDisponibilities() {
        return actualDisponibilities;
    }

    public void setActualDisponibilities(Map<Pair<Integer, Integer>, Status[][]> actualDisponibilities) {
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
