package Metier.Modele;

import Metier.Modele.Appointment;
import Metier.Modele.Payment.Method;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-23T21:57:54", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Payment.class)
public class Payment_ { 

    public static volatile SingularAttribute<Payment, Boolean> emmited;
    public static volatile SingularAttribute<Payment, Method> paymentMethod;
    public static volatile SingularAttribute<Payment, Appointment> appointment;
    public static volatile SingularAttribute<Payment, Boolean> received;
    public static volatile SingularAttribute<Payment, Long> id;

}