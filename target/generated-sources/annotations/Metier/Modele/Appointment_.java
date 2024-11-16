package Metier.Modele;

import Metier.Modele.Appointment.Status;
import Metier.Modele.Client;
import Metier.Modele.Payment;
import Metier.Modele.Publication;
import Utils.Pair;
import java.util.Date;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-16T22:51:36", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Appointment.class)
public class Appointment_ { 

    public static volatile MapAttribute<Appointment, Integer, Set> duration;
    public static volatile SingularAttribute<Appointment, Date> date;
    public static volatile SingularAttribute<Appointment, Integer> note;
    public static volatile SingularAttribute<Appointment, Pair> dateAppointment;
    public static volatile SingularAttribute<Appointment, Integer> price;
    public static volatile SingularAttribute<Appointment, Publication> publication;
    public static volatile SingularAttribute<Appointment, Client> client;
    public static volatile SingularAttribute<Appointment, Payment> payment;
    public static volatile SingularAttribute<Appointment, Long> id;
    public static volatile SingularAttribute<Appointment, Status> status;

}