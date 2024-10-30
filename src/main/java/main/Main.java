/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import DAO.JpaUtil;
import Metier.Modele.Client;

import Metier.Modele.WorkType;
import Metier.Service.Service;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author DeLL
 */
public class Main {
    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();

        
        Service.signUp("selim", "ben", "mail", "pass", "07809", "France");
        Service.signUp("Adrian", "bbb", "mail.com", "pass", "171717", "address");
        Service.addWorkType("travail");
        Service.publish((long)1,new Date(), "travail", 20, "title", "descrpition");
        
        Service.setTrueClientDisponibility(3, 5, (long) 1);
        Service.setTrueClientDisponibility(3, 6, (long) 1);
        Service.setTrueClientDisponibility(3, 7, (long) 1);
        Service.setTrueClientDisponibility(3, 8, (long) 1);
        Service.setTrueClientDisponibility(3, 9, (long) 1);
        Service.setTrueClientDisponibility(3, 10, (long) 1);
        
        
        Service.takeAppointment((long)2, (long)1, LocalDate.of(2024, Month.NOVEMBER, 14), 5, 5);
        Client client = Service.getClientById((long) 1);
        
        
        
        JpaUtil.fermerFabriquePersistance();
    }
    
}
