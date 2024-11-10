package Metier.Modele;

import Metier.Modele.Appointment.Status;
import Metier.Modele.Client;
import Metier.Modele.Publication;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-10T01:21:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Appointment.class)
public class Appointment_ { 

    public static volatile SingularAttribute<Appointment, Integer> duration;
    public static volatile SingularAttribute<Appointment, LocalDate> date;
    public static volatile SingularAttribute<Appointment, Integer> note;
    public static volatile SingularAttribute<Appointment, Publication> publication;
    public static volatile SingularAttribute<Appointment, Boolean> paid;
    public static volatile SingularAttribute<Appointment, Integer> start;
    public static volatile SingularAttribute<Appointment, Client> client;
    public static volatile SingularAttribute<Appointment, Long> id;
    public static volatile SingularAttribute<Appointment, Status> status;

}