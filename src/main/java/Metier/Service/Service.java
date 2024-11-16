/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Service;

import DAO.AdminDAO;
import DAO.AppointmentDAO;
import DAO.ClientDAO;
import DAO.JpaUtil;
import DAO.PaymentDAO;
import DAO.PublicationDAO;
import DAO.WorkTypeDAO;
import Metier.Modele.Admin;
import Metier.Modele.Appointment;
import Metier.Modele.Client;
import Metier.Modele.Payment;
import Metier.Modele.Publication;
import Metier.Modele.WorkType;
import Utils.Pair;
import com.google.maps.model.LatLng;
import com.sun.net.httpserver.Authenticator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//TODO : Change signup with API functionning 
//TODO : Change takeAppointment with API functionning


/**
 *
 * @author DeLL
 */
public class Service {
    public static Client authenticate(String mail, String password) {
        Client client = null;
        ClientDAO clientDao = new ClientDAO();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            JpaUtil.creerContextePersistance();            
            client = clientDao.findByMail(mail);
            if (client == null || !passwordEncoder.matches(password, client.getPassword())) {
                client = null;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return client;
    }
    
    
    public static boolean signUp(String firstname, String lastname, String mail, String rawPassword, String phonenumber, String address) {
        boolean success = false;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword = passwordEncoder.encode(rawPassword);
        Client client = new Client(firstname, lastname, mail, phonenumber, hashPassword, address);
        LatLng coords = null;//GeoNetApi.getLatLng(address);
        if (coords != null) {
            client.setLatitude(coords.lat);
            System.out.println(coords.lat);
            client.setLongitude(coords.lng);
            success = true;
        } else {
            success = true;
        }
        ClientDAO clientDao = new ClientDAO();
        
        
        if (success) {
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                clientDao.create(client);
                JpaUtil.validerTransaction();
                success = true;
            } catch (Exception ex) {
                JpaUtil.annulerTransaction();
                success = false;

            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        return success;        
    }
    
    
    public static Client getClientById(Long id) {
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            client = clientDAO.findById(id);
        } catch (Exception ex){
            System.err.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return client;
    }
    
    
    public static boolean setFreeActualDisponibility (int day, int hour, Long id, LocalDate date) {
        boolean result = true;
        Map<Pair<Integer, Integer> ,Client.Status [][]> disponibility;
        int year = date.getYear();
        int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        Client.Status[][] table;
        ClientDAO clientDAO = new ClientDAO();
        Client client = null;
        if (day<0 || day > 6 || hour < 0 || hour > 11) {
            result = false;
        }
        
        try {
            if (result) {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                client = clientDAO.findById(id);
                disponibility = client.getActualDisponibilities();
                table = disponibility.get(new Pair(year, weekOfYear));
                table[day][hour] = Client.Status.FREE;
                client.addDisponibility(date, table);
                clientDAO.update(client);
                JpaUtil.validerTransaction();
            }
            
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    
    public static boolean setNotFreeActualDisponibility (int day, int hour, Long id, LocalDate date) {
        boolean result = true;
        ClientDAO clientDAO = new ClientDAO();
        Client client = null;
        Map<Pair<Integer, Integer> , Client.Status[][]> disponibility;
        int year = date.getYear();
        int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        Client.Status[][] table;
        if (day<0 || day > 6 || hour < 0 || hour > 11) {
            result = false;
        }
        
        try {
            if (result) {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                client = clientDAO.findById(id);
                disponibility = client.getActualDisponibilities();
                table = disponibility.get(new Pair(year, weekOfYear));
                table[day][hour] = Client.Status.NOT_FREE;
                client.addDisponibility(date, table);
                clientDAO.update(client);
                JpaUtil.validerTransaction();
            }
            
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    
    
    
    public static boolean setNotTakenActualDisponibility (int day, int hour, Long id, LocalDate date) {
        boolean result = true;
        ClientDAO clientDAO = new ClientDAO();
        Client client = null;
        Map<Pair<Integer, Integer> , Client.Status[][]> disponibility;
        int year = date.getYear();
        int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        Client.Status[][] table;
        if (day<0 || day > 6 || hour < 0 || hour > 11) {
            result = false;
        }
        
        try {
            if (result) {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                client = clientDAO.findById(id);
                disponibility = client.getActualDisponibilities();
                table = disponibility.get(new Pair(year, weekOfYear));
                table[day][hour] = Client.Status.TAKEN;
                client.addDisponibility(date, table);
                clientDAO.update(client);
                JpaUtil.validerTransaction();
            }
            
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    
    public static boolean InitializeClientDisponibility(Long id) {
        boolean result = false;
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDAO.findById(id);
            client.setClientDisponibilities(new boolean[7][12]);
            clientDAO.update(client);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;    
    }
    
    
    public static boolean setTrueClientDisponibility (int day, int hour, Long id) {
        boolean result = true;
        ClientDAO clientDAO = new ClientDAO();
        Client client = null;
        boolean [][] disponibility;
        boolean [][] newDispo = new boolean[7][12];
        if (day<0 || day > 6 || hour < 0 || hour > 11) {
            result = false;
        }
        
        try {
            if (result) {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                client = clientDAO.findById(id);
                disponibility = client.getClientDisponibilities();
                disponibility[day][hour] = true;
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j< 12; j++){
                        newDispo[i][j] |= disponibility[i][j];
                    }
                }
                
                client.setClientDisponibilities(newDispo);
                clientDAO.update(client);
                JpaUtil.validerTransaction();
            }
            
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    
    public static boolean setClientDisponibility(boolean disponibility[][], Long id) {
        boolean result = true;
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        
        //Assertion to verify the length
        assert(disponibility.length == 7);
        for (boolean[] element : disponibility) {
            assert(element.length == 12);
        }

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDAO.findById(id);
            client.setClientDisponibilities(disponibility);
            clientDAO.update(client);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    public static boolean setActualDisponibility(Long id, LocalDate date) {
        boolean result = true;
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDAO.findById(id);
            client.addDisponibility(date);
            clientDAO.update(client);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static boolean setActualDisponibility(Client.Status[][] disponibility, Long id, LocalDate date) {
        boolean result = true;
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        if (disponibility.length != 7) {
            result = false;
        }
        
        for (Client.Status[] element : disponibility) {
            if (element.length != 12) {
                result = false;
                break;
            }
        }
         
        if(result) {
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                client = clientDAO.findById(id);
                client.addDisponibility(date, disponibility);
                clientDAO.update(client);
                JpaUtil.validerTransaction();
            } catch (Exception ex) {
                result = false;
                JpaUtil.annulerTransaction();
            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        
        return result;
    }
    
    
    public static boolean setFalseClientDisponibility (int day, int hour, Long id) {
        boolean result = true;
        ClientDAO clientDAO = new ClientDAO();
        Client client = null;
        boolean [][] disponibility;
        if (day<0 || day > 6 || hour < 0 || hour > 11) {
            result = false;
        }
        
        try {
            if (result) {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                client = clientDAO.findById(id);
                disponibility = client.getClientDisponibilities();
                disponibility[day][hour] = false;
                client.setClientDisponibilities(disponibility);
                clientDAO.update(client);
                JpaUtil.validerTransaction();
            }
            
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    
    public static boolean publish(Long id, Date date, String workTypeString, int price, String title, String descrption, Double distanceMax) {
        boolean result = false;
        WorkType workType = null;
        WorkTypeDAO workTypeDAO = new WorkTypeDAO();
        Publication publication = null;
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            workType = workTypeDAO.findById(workTypeString);
            publication = new Publication(date, workType, price, title, descrption, distanceMax);
            client = clientDAO.findById(id);
            publication.setClient(client);
            client.addPublication(publication);
            clientDAO.update(client);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return result;
    }
    
    
    public static boolean takeAppointment(Long idClient, Long idPublication, LocalDate date, Map<Integer, Set<Integer>> map) {
        boolean result = true;
        Client client = null;
        Client worker = null;
        Publication publication = null;
        Appointment appointment = null;
        ClientDAO clientDAO = new ClientDAO();
        PublicationDAO publicationDAO = new PublicationDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Client.Status[][] workerActualDispo = null;
        Client.Status[][] workerNewActualDispo = new Client.Status[7][12];
        boolean[][] workerDispo = null;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDAO.findById(idClient);
            publication = publicationDAO.findById(idPublication);
            worker = publication.getClient();
            workerActualDispo = worker.getActualDisponibilities(date);
            if (workerActualDispo == null) {
                workerActualDispo = worker.addDisponibility(date);
            }
            for (int i = 0; i<7; i++) {
                System.arraycopy(workerActualDispo[i], 0, workerNewActualDispo[i], 0, 12);
            }
            workerDispo = worker.getClientDisponibilities();
            
            for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
                Integer day = entry.getKey();
                Set<Integer> hourSet = entry.getValue();
                for (Integer hour : hourSet) {
                    if (!(workerDispo[day][hour] || !(workerActualDispo[day][hour] == Client.Status.FREE))) {
                        result = false;
                        break;
                    } else {
                        workerNewActualDispo[day][hour] = Client.Status.TAKEN;
                    }
                }
                if (!result) {
                    break;
                }
            }
            
            if (result) {
                worker.addDisponibility(date, workerNewActualDispo);
                appointment = new Appointment(date, client, publication, map);
                appointmentDAO.create(appointment);
                clientDAO.update(worker);
            }
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            result = false;
            System.out.println(ex);
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        } 
        return result;
    }
     
    
    public static boolean addWorkType(String workTypeString) {
        boolean result = false;
        WorkType workType = new WorkType(workTypeString);
        WorkTypeDAO workTypeDAO = new WorkTypeDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            workTypeDAO.create(workType);
            JpaUtil.validerTransaction();
            result = true;
        } catch(Exception ex) {
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }

    public static boolean validatePublication(Long id) {
        boolean result = false;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            publication = publicationDAO.findById(id);
            publication.setStatus(Publication.Status.APPROVED);
            publicationDAO.update(publication);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
               
    }
    
    
    public static boolean rejectPublication(Long id) {
        boolean result = false;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            publication = publicationDAO.findById(id);
            publication.setStatus(Publication.Status.REJECTED);
            publicationDAO.update(publication);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    public static boolean validatePaymentFromWorker(Long id, Payment.Method method) {
        boolean result = false;
        Payment payment = null;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointment = appointmentDAO.findById(id);
            payment = appointment.getPayment();
            if (payment == null) {
                payment = new Payment(method, appointment);
            } else if (payment.getPaymentMethod() == null) {
                payment.setPaymentMethod(method);
            }
            payment.setEmmited(true);
            payment.setReceived(true);
            appointment.setPayment(payment);
            appointmentDAO.update(appointment);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    public static boolean validatePaymentFromClient(Long id, Payment.Method method) {
        boolean result = false;
        Payment payment = null;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointment = appointmentDAO.findById(id);
            payment = appointment.getPayment();
            if (payment == null) {
                payment = new Payment(method, appointment);
            } else {
                payment.setPaymentMethod(method);
            }
            payment.setEmmited(true);
            appointment.setPayment(payment);
            appointmentDAO.update(appointment);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    
    public static boolean updatePublicationTitle(Long id, String title) {
        boolean result = false;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            publication = publicationDAO.findById(id);
            publication.setTitle(title);
            publicationDAO.update(publication);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    public static boolean updatePublicationDescrption(Long id, String description) {
        boolean result = false;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            publication = publicationDAO.findById(id);
            publication.setDescription(description);
            publicationDAO.update(publication);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    public static boolean updatePublicationPrice(Long id, Integer price) {
        boolean result = false;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            publication = publicationDAO.findById(id);
            publication.setPrice(price);
            publicationDAO.update(publication);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
   /*  
    public static boolean cancelAppointment(Long id) {
        boolean result = false;
        Client worker = null;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        PublicationDAO publicationDAO = new PublicationDAO();
        ClientDAO clientDAO = new ClientDAO();
        boolean[][] clientDisponibility;
        Client.Status[][] actualDisponibility;
        int start;
        int duration;
        int day;
        LocalDate date;
        
        
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointment = appointmentDAO.findById(id);
            worker = appointment.getPublication().getClient();
            if (appointment.getDate().isAfter(LocalDate.now())) {
                result = false;
            } else {
                appointment.setStatus(Appointment.Status.CANCELLED);
                date = appointment.getDate();
                day = date.getDayOfWeek().getValue() - 1;
                start = appointment.getStart();
                duration = appointment.getDuration();

                clientDisponibility = worker.getClientDisponibilities();
                actualDisponibility = worker.getActualDisponibilities().get(date);

                for (int i = 0; i<duration; i++) {
                    if (clientDisponibility[day][start+1]) {
                        actualDisponibility[day][start+i] = Client.Status.FREE;
                    } else {
                        actualDisponibility[day][start+i] = Client.Status.NOT_FREE;
                    }
                    
                }
                worker.addDisponibility(date, actualDisponibility);
                clientDAO.update(worker);
                appointmentDAO.update(appointment);
                result = true;
            }
            
            JpaUtil.validerTransaction();
            
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    } */
    
    
    public static boolean cancelAppointment(Long id) {
        boolean result = false;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        Map<Integer, Set<Integer>> duration;
        boolean[][]  disponibilities;
        Client.Status[][] newActualDispo = new Client.Status[7][12];
        Client.Status[][] actualDispo;
        int year;
        int week;
        LocalDate localDate;
        
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointment = appointmentDAO.findById(id);
            client = appointment.getPublication().getClient();
            disponibilities = client.getClientDisponibilities();
            year = appointment.getDateAppointment().getFirst();
            week = appointment.getDateAppointment().getSecond();
            localDate = LocalDate.of(year, 1, 1)
                .with(WeekFields.of(Locale.getDefault()).weekOfYear(), week)
                .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
            actualDispo = client.getActualDisponibilities(localDate);
            duration = appointment.getDuration();
            
            for (int i = 0; i<7; i++) {
                System.arraycopy(actualDispo[i], 0, newActualDispo[i], 0, 12);
            }
            
            for (Map.Entry<Integer, Set<Integer>> entry : duration.entrySet()) {
                int day = entry.getKey();
                for (int hour : entry.getValue()) {
                    newActualDispo[day][hour] = disponibilities[day][hour] ? Client.Status.FREE : Client.Status.NOT_FREE;
                }
            }
            
            client.addDisponibility(localDate, newActualDispo);
            appointment.setStatus(Appointment.Status.CANCELLED);
            clientDAO.update(client);
            appointmentDAO.update(appointment);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            System.out.println(ex);
            result = false;
        }
        
        return result;
    }
    
   
       
    public static boolean validateAppointment(Long id) {
        boolean result = false;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Payment payment = null;
        Pair<Integer, Integer> yearWeek;
        Map<Integer, Set<Integer>> duration;

        // Obtenir la dernière date et heure de l'appointment
        int lastDayOfWeek;
        Set<Integer> lastHoursSet;
        int lastHour;

        // Convertir l'année et la semaine en date
        LocalDate startOfWeek;  // Premier jour de la semaine

        // Calculer la date et heure exacte du dernier créneau
        LocalDateTime endDateTime;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointment = appointmentDAO.findById(id);
            
            yearWeek = appointment.getDateAppointment();
            duration  = appointment.getDuration();
            lastDayOfWeek = duration.keySet().stream().max(Integer::compareTo).orElse(0);
            lastHoursSet = duration.get(lastDayOfWeek);
            lastHour = lastHoursSet.stream().max(Integer::compareTo).orElse(0);
            startOfWeek = LocalDate.of(yearWeek.getFirst(), 1, 1)
                .with(WeekFields.ISO.weekOfYear(), yearWeek.getSecond())
                .with(WeekFields.ISO.dayOfWeek(), 1);
            endDateTime = startOfWeek.plusDays(lastDayOfWeek - 1)
                .atTime(9 + lastHour, 0);
            
            if (!endDateTime.isBefore(LocalDateTime.now())) {
                result = false;
            } else {
                appointment.setStatus(Appointment.Status.PASSED);
                payment = new Payment(null, appointment);
                appointment.setPayment(payment);
                appointmentDAO.update(appointment);
                result = true;
        }
            JpaUtil.validerTransaction();            
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    public static boolean addNote(Long id, Integer note) {
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        int numberNotes;
        boolean result = true;
        if (note < 0 || note >4) {
            result = false;
        }
        
        if (result) {
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                appointment = appointmentDAO.findById(id);
                publication = appointment.getPublication();
                numberNotes = publication.getNumberNotes();
                publication.setAverage(((publication.getAverage()== null ? 0 : publication.getAverage())*numberNotes+note)/(numberNotes+1));
                appointment.setNote(note);
                publication.setNumberNotes(numberNotes+1);
                publicationDAO.update(publication);
                appointmentDAO.update(appointment);
                JpaUtil.validerTransaction();                
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
                JpaUtil.annulerTransaction();
                result = false;
            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        
        return result;
    }
    
    
    public static Integer getNote(Long id) {
        Integer result = null;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            appointment = appointmentDAO.findById(id);
            result = appointment.getNote();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    
    
    public static Float getAverage(Long id) {
        Float result = null;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            publication = publicationDAO.findById(id);
            result = publication.getAverage();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    public static Appointment getAppointmentById(Long id) {
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            appointment = appointmentDAO.findById(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return appointment;
    }
    
    public static Payment getPaymentById(Long id) {
        Payment payment = null;
        PaymentDAO paymentDAO = new PaymentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            payment = paymentDAO.findById(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return payment;
    }
    
    public static Client.Status[][] getActualDispo(Long id, LocalDate date) {
        Client.Status[][] result = null;
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDAO.findById(id);
            result = client.getActualDisponibilities(date);
            if (result == null) {
                result = client.addDisponibility(date);
                clientDAO.update(client);
            }
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static Publication getPublicationById(Long id) {
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            publication = publicationDAO.findById(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return publication;
    }
    
    public static List<WorkType> getWorkTypeList() {
        List<WorkType> result = null;
        WorkTypeDAO workTypeDAO = new WorkTypeDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = workTypeDAO.getList();
        } catch (Exception ex){
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    public static WorkType getWorkTypeById(String id) {
        WorkType workType = null;
        WorkTypeDAO workTypeDAO = new WorkTypeDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            workType = workTypeDAO.findById(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return workType;
    }
    
    
    public static boolean setClientFirstName(Long id, String firstName) {
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        boolean result = false;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDAO.findById(id);
            client.setFirstName(firstName);
            clientDAO.update(client);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static boolean setDistanceMax(Long id, Double distanceMax) {
        boolean result = false;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            publication = publicationDAO.findById(id);
            publication.setDistanceMax(distanceMax);
            publicationDAO.update(publication);
            JpaUtil.validerTransaction();
            result = true;                    
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            System.out.println(ex);
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    public static List<Publication> getListPublicationDistance(double distance, String workType) {
        List<Publication> result = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        WorkTypeDAO workTypeDAO = new WorkTypeDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            if (workType == null) {
                result = publicationDAO.getListApproved(distance);
            }
            else {
                result = publicationDAO.getListApprovedByWorkType(distance, workTypeDAO.findById(workType));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    public static List<Publication> getListPublicationWaiting() {
        List<Publication> result = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = publicationDAO.getListWaiting();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    public static boolean isClientPublication(Long clientId, Long publicationId) {
        ClientDAO clientDAO = new ClientDAO();
        PublicationDAO publicationDAO = new PublicationDAO();
        boolean result = false;
        
        try {
            JpaUtil.creerContextePersistance();
            result = (clientDAO.findById(clientId) == publicationDAO.findById(publicationId).getClient());
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    
    public static List<Appointment> getNextsAppointmentAsClient (Long id) {
        List<Appointment> result = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = appointmentDAO.getNextsAppointmentAsClient(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static List<Appointment> getNextsAppointmentAsWorker (Long id) {
        List<Appointment> result = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = appointmentDAO.getNextsAppointmentAsWorker(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static List<Appointment> getPassedAppointmentAsClient (Long id) {
        List<Appointment> result = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = appointmentDAO.getPassedAppointmentsAsClient(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static List<Appointment> getPassedAppointmentAsWorker (Long id) {
        List<Appointment> result = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = appointmentDAO.getPassedAppointmentsWorker(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    
    
    
    public static boolean suppressPublication(Long id) {
        boolean result = false;
        Publication publication = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            publication = publicationDAO.findById(id);
            publicationDAO.delete(publication);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static List<Appointment> getToPayAppointment(Long id) {
        List<Appointment> result = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = appointmentDAO.getToPayAppointment(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    
    public static Integer getPriceOfAppointment(Long id) {
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Integer result;
        try {
            JpaUtil.creerContextePersistance();
            appointment = appointmentDAO.findById(id);
            result = appointment.getPrice();
        } catch (Exception ex) {
            System.out.println(ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }       
        
        return result;
    }
    
    
    public static boolean addAdmin(String mail, String rawPassword) {
        boolean result = false;
        Admin admin = null;
        AdminDAO adminDAO = new AdminDAO();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword = passwordEncoder.encode(rawPassword);
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            admin = new Admin(mail, hashPassword);
            adminDAO.create(admin);
            JpaUtil.validerTransaction();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        return result;
    }
    
    public static Admin authenticateAdmin(String mail, String password) {
        Admin admin = null;
        AdminDAO adminDAO = new AdminDAO();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        try {
            JpaUtil.creerContextePersistance();
            admin = adminDAO.findByMail(mail);
            if (admin == null || !passwordEncoder.matches(password, admin.getHashedPassword())) {
                admin = null;
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return admin;
    }
    
    
    public static List<Client> getListClient() {
        List<Client> result = null;
        ClientDAO clientDAO = new ClientDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = clientDAO.getListClient();
        } catch (Exception ex) {
            System.out.println(ex);            
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
    public static Admin getAdminById(Long id) {
        Admin admin = null;
        AdminDAO adminDAO = new AdminDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            admin = adminDAO.findById(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return admin;
    }
    
    
    public static boolean updatePassedAppointments() {
        boolean result = true;
        List<Appointment> appointmentList;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointmentList = appointmentDAO.getPassedAppointments();
            
            for (Appointment appointment : appointmentList) {
                Pair<Integer, Integer> yearWeek = appointment.getDateAppointment();
                Map<Integer, Set<Integer>> duration  = appointment.getDuration();
                int lastDayOfWeek = duration.keySet().stream().max(Integer::compareTo).orElse(0);
                Set<Integer> lastHoursSet = duration.get(lastDayOfWeek);
                int lastHour = lastHoursSet.stream().max(Integer::compareTo).orElse(0);
                LocalDate startOfWeek = LocalDate.of(yearWeek.getFirst(), 1, 1)
                    .with(WeekFields.ISO.weekOfYear(), yearWeek.getSecond())
                    .with(WeekFields.ISO.dayOfWeek(), 1);
                LocalDateTime endDateTime = startOfWeek.plusDays(lastDayOfWeek - 1)
                    .atTime(9 + lastHour, 0);
                
                if (!endDateTime.isBefore(LocalDateTime.now())) {
                    break;
                } else {
                    appointment.setStatus(Appointment.Status.PASSED);
                    Payment payment = new Payment(null, appointment);
                    appointment.setPayment(payment);
                    appointmentDAO.update(appointment);
                }                
            }
            JpaUtil.validerTransaction();
            result =true;
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            System.out.println(ex);
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        
        return result;
    }
    
    
    public static List<Appointment> getCanceledAppointmentAsClient (Long id) {
        List<Appointment> result = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = appointmentDAO.getCanceledAppointmentsAsClient(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
    
    public static List<Appointment> getCanceledAppointmentAsWorker (Long id) {
        List<Appointment> result = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = appointmentDAO.getCanceledAppointmentsAsWorker(id);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }
    
}