/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier.Service;

import DAO.AppointmentDAO;
import DAO.ClientDAO;
import DAO.JpaUtil;
import DAO.PaymentDAO;
import DAO.PublicationDAO;
import DAO.WorkTypeDAO;
import Metier.Modele.Appointment;
import Metier.Modele.Client;
import Metier.Modele.Payment;
import Metier.Modele.Publication;
import Metier.Modele.WorkType;
import com.google.maps.model.LatLng;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        try {
            JpaUtil.creerContextePersistance();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            client = clientDao.findByMail(mail);
            if (!passwordEncoder.matches(password, client.getPassword())) {
                client = null;
                System.out.println("Petit test ici");
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
        HashMap<LocalDate ,Client.Status [][]> disponibility;
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
                table = disponibility.get(date);
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
        HashMap<LocalDate , Client.Status[][]> disponibility;
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
                table = disponibility.get(date);
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
        HashMap<LocalDate , Client.Status[][]> disponibility;
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
                table = disponibility.get(date);
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
    
    
    public static boolean setTrueClientDisponibility (int day, int hour, Long id) {
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
                disponibility[day][hour] = true;
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
    
    
    public static boolean setClientDisponibility(boolean disponibility[][], Long id) {
        boolean result = true;
        Client client = null;
        ClientDAO clientDAO = new ClientDAO();
        if (disponibility.length != 7) {
            result = false;
        }
        
        for (boolean[] element : disponibility) {
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
                client.setClientDisponibilities(disponibility);
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
    
    
    public static boolean takeAppointment(Long idClient, Long idPublication, LocalDate date, int duration, int start) {
        boolean result = true;
        LatLng coordClient = null;
        LatLng coordWorker = null;
        Client client = null;
        Client worker = null;
        Publication publication = null;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        ClientDAO clientDAO = new ClientDAO();
        PublicationDAO publicationDAO = new PublicationDAO();
        int day = date.getDayOfWeek().getValue() - 1;
        boolean[][] workerDispo;
        Client.Status[][] workerActualDispo = null;
        
        if (start<0 || duration <0 || start+duration>11) {
            result = false;
        }

        
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDAO.findById(idClient);
            publication = publicationDAO.findById(idPublication);
            worker = publication.getClient();            
            workerDispo = worker.getClientDisponibilities();
            /*if (result) {
                coordClient =  new LatLng(client.getLatitude(), client.getLongitude());
                coordWorker = new LatLng(worker.getLatitude(), worker.getLongitude());
                if (GeoNetApi.getFlightDistanceInKm(coordClient, coordWorker) > publication.getDistanceMax()) {
                    result = false;
                }
            } */
            
            
            
            //If the index are goods
            if (result) {
                //We verify that the worker said he's ok with these dates
                for (int i = 0; i<duration; i++) {
                    System.out.println(workerDispo[day][start+1]);
                    if (workerDispo[day][start+1] == false) {
                        result = false;
                        break;
                    }
                }
            }
            
            
            //If the index are good and the worker noramlly have the good disonibilities
            if (result) {
                workerActualDispo = worker.getActualDisponibilities().get(date);
                //If the hashma does not contains the correct date, we add it by default
                if (workerActualDispo == null) {
                    worker.addDisponibility(date);
                    workerActualDispo = worker.getActualDisponibilities().get(date);
                    
                } else { //If it contains it, we verify the worker does not have an appointment at this hour
                    for (int i = 0; i<duration; i++) {
                        if (workerActualDispo[day][start+1] == Client.Status.TAKEN) {
                            result = false;
                            break;
                        }
                    }
                }
            }
            
            //If the appointment can be taken
            if (result) {
                //We update the worker disponibilities
                for (int i = 0; i<duration; i++) {
                    workerActualDispo[day][start+i] = Client.Status.TAKEN;
                }
                worker.addDisponibility(date, workerActualDispo);
                
                appointment = new Appointment(duration, date, start, client, publication);
                appointmentDAO.create(appointment);
                clientDAO.update(worker);
            }
            
            JpaUtil.validerTransaction();            
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            result = false;
            System.out.println(ex);
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
    
    public static boolean validatePayment(Long id, Payment.Method method) {
        boolean result = false;
        Payment payment = null;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointment = appointmentDAO.findById(id);
            payment = new Payment(method, appointment);
            paymentDAO.create(payment);
            appointment.setPaid(true);
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
    }
    
    
    public static boolean validateAppointment(Long id) {
        boolean result = false;
        Appointment appointment = null;
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            appointment = appointmentDAO.findById(id);
            
            if (appointment.getDate().isAfter(LocalDate.now())) {
                result = false;
            } else {
                appointment.setStatus(Appointment.Status.PASSED);
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
                publication.setAverage((publication.getAverage()*numberNotes+note)/(numberNotes+1));
                appointment.setNote(note);
                publication.setNumberNotes(numberNotes+1);
                publicationDAO.update(publication);
                appointmentDAO.update(appointment);
                JpaUtil.validerTransaction();                
            } catch (Exception ex) {
                System.out.println(ex);
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
    
    public static List<Publication> getListPublicationDistance(double distance) {
        List<Publication> result = null;
        PublicationDAO publicationDAO = new PublicationDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            result = publicationDAO.getList(distance);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return result;
    }
    
    
}
