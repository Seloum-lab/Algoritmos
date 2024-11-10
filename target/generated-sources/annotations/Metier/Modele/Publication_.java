package Metier.Modele;

import Metier.Modele.Client;
import Metier.Modele.Publication.Status;
import Metier.Modele.WorkType;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-10T01:21:51", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Publication.class)
public class Publication_ { 

    public static volatile SingularAttribute<Publication, Date> date;
    public static volatile SingularAttribute<Publication, Double> distanceMax;
    public static volatile SingularAttribute<Publication, Integer> numberNotes;
    public static volatile SingularAttribute<Publication, Float> average;
    public static volatile SingularAttribute<Publication, Integer> price;
    public static volatile SingularAttribute<Publication, WorkType> workType;
    public static volatile SingularAttribute<Publication, String> description;
    public static volatile SingularAttribute<Publication, Client> client;
    public static volatile SingularAttribute<Publication, Long> id;
    public static volatile SingularAttribute<Publication, String> title;
    public static volatile SingularAttribute<Publication, Status> status;

}