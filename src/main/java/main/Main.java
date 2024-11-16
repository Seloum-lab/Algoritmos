/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import DAO.JpaUtil;
import Metier.Modele.Appointment;
import Metier.Modele.Client;
import Metier.Modele.Payment.Method;

import Metier.Modele.WorkType;
import Metier.Service.Service;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author DeLL
 */
public class Main {
    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        
        // Initialisation des disponibilités par défaut (toutes disponibles)
        boolean[][] defaultDisponibility = new boolean[7][12];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 12; j++) {
                defaultDisponibility[i][j] = true;
            }
        }

        // Ajouter l'administrateur
        Service.addAdmin("admin@mail.com", "securepassword");

        // Création des clients (15 clients)
        for (int i = 1; i <= 15; i++) {
            String firstName = "Client" + i;
            String lastName = "Last" + i;
            String email = "client" + i + "@mail.com";
            String phone = "070123456" + i;
            String address = i + " Some Street";
            Service.signUp(firstName, lastName, email, "password", phone, address);
        }

        // Initialisation des disponibilités pour chaque client
        for (long clientId = 1; clientId <= 15; clientId++) {
            Service.InitializeClientDisponibility(clientId);
            Service.setClientDisponibility(defaultDisponibility, clientId);
        }

        // Ajouter des types de travail
        Service.addWorkType("Plomberie");
        Service.addWorkType("Menuiserie");
        Service.addWorkType("Électricité");
        Service.addWorkType("Jardinage");
        Service.addWorkType("Peinture");
        Service.addWorkType("Maçonnerie");
        Service.addWorkType("Nettoyage");

        // Publier plusieurs offres pour chaque client (2 publications par client)
        for (long clientId = 1; clientId <= 15; clientId++) {
            Service.publish(clientId, new Date(), "Plomberie", 50, "Réparation de fuite", "Besoin d'un plombier pour une fuite dans la salle de bain", 40.5);
            Service.publish(clientId, new Date(), "Menuiserie", 30, "Installation de meubles", "Monter des meubles IKEA", 45.0);
        }

        // Certaines publications ne sont pas validées
        for (long publicationId = 1; publicationId <= 20; publicationId++) {
            if (publicationId <= 15) {
                Service.validatePublication(publicationId); // Seules les 15 premières sont validées
            }
        }

        // Ajouter plusieurs rendez-vous (3 rendez-vous par publication, y compris dans le passé)
        LocalDate now = LocalDate.now();
        
        // Rendez-vous dans le passé (en 2023)
        Map<Integer, Set<Integer>> schedule1 = new HashMap<>();
        schedule1.put(0, new HashSet<>(Arrays.asList(1, 2, 3))); // Lundi 9h-12h
        Service.takeAppointment(1L, 1L, now.minusYears(2).minusMonths(1), schedule1); // Rendez-vous dans le passé

        Map<Integer, Set<Integer>> schedule2 = new HashMap<>();
        schedule2.put(1, new HashSet<>(Arrays.asList(5, 6))); // Mardi 14h-16h
        Service.takeAppointment(1L, 2L, now.minusMonths(1), schedule2); // Rendez-vous dans le passé

        Map<Integer, Set<Integer>> schedule3 = new HashMap<>();
        schedule3.put(2, new HashSet<>(Arrays.asList(8, 9))); // Mercredi 16h-18h
        Service.takeAppointment(2L, 3L, now.minusMonths(2), schedule3); // Rendez-vous dans le passé

        Map<Integer, Set<Integer>> schedule4 = new HashMap<>();
        schedule4.put(3, new HashSet<>(Arrays.asList(10, 11))); // Jeudi 17h-19h
        Service.takeAppointment(2L, 4L, now.minusMonths(3), schedule4); // Rendez-vous dans le passé

        // Rendez-vous actuels (aujourd'hui et demain)
        for (long publicationId = 1; publicationId <= 10; publicationId++) {
            for (int i = 1; i <= 3; i++) {
                Map<Integer, Set<Integer>> schedule = new HashMap<>();
                schedule.put(i, new HashSet<>(Arrays.asList(i, i + 1, i + 2)));
                Service.takeAppointment(publicationId, publicationId + 1, now, schedule);
            }
        }

        // Rendez-vous futurs (dans plusieurs mois)
        for (long publicationId = 11; publicationId <= 20; publicationId++) {
            for (int i = 1; i <= 3; i++) {
                Map<Integer, Set<Integer>> schedule = new HashMap<>();
                schedule.put(i + 3, new HashSet<>(Arrays.asList(i + 5, i + 6, i + 7)));
                Service.takeAppointment(publicationId, publicationId + 1, now.plusMonths(6), schedule);
            }
        }

        // Paiements et validations des rendez-vous
        Service.validatePaymentFromClient(1L, Method.CARD);
        Service.validatePaymentFromWorker(1L, Method.CARD);

        Service.validatePaymentFromClient(2L, Method.CASH);
        Service.validatePaymentFromWorker(2L, null);

        // Annuler un rendez-vous
        Service.cancelAppointment(2L); // Annuler un rendez-vous
        
        Service.updatePassedAppointments();

        // Finalisation
        JpaUtil.fermerFabriquePersistance();
    }
}
