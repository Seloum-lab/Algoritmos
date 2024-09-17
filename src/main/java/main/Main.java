/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import DAO.JpaUtil;
import Metier.Service.Service;

/**
 *
 * @author DeLL
 */
public class Main {
    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        Service service = new Service();
        
        service.signUp("selim", "ben", "mail", "pass", "07809", "France");
        
        
        
        JpaUtil.fermerFabriquePersistance();
    }
    
}
