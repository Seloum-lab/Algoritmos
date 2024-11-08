package Metier.Modele;

import Metier.Modele.Publication;
import java.util.HashMap;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-08T01:51:50", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, String> firstName;
    public static volatile SingularAttribute<Client, String> lastName;
    public static volatile SingularAttribute<Client, boolean[][]> clienDisponibilities;
    public static volatile SingularAttribute<Client, String> password;
    public static volatile SingularAttribute<Client, String> phoneNumber;
    public static volatile SingularAttribute<Client, String> address;
    public static volatile SingularAttribute<Client, String> mail;
    public static volatile SingularAttribute<Client, Double> latitude;
    public static volatile SingularAttribute<Client, Long> id;
    public static volatile SingularAttribute<Client, HashMap> actualDisponibilities;
    public static volatile SetAttribute<Client, Publication> publications;
    public static volatile SingularAttribute<Client, Double> longitude;

}