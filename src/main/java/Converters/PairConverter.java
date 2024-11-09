/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;



/**
 *
 * @author DeLL
 */
import Utils.Pair;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.foundation.AbstractCompositeCollectionMapping;


public class PairConverter implements Converter {

    private static final String SEPARATOR = ", ";
    // Convertit l'objet Pair en une chaîne (par exemple "10,20")
    @Override
    public Object convertObjectValueToDataValue(Object objectValue, Session session) {
        Pair<Integer, Integer> pair = (Pair<Integer, Integer>) objectValue;
        if (pair == null) {
            return null;
        }
        return pair.getFirst() + SEPARATOR + pair.getSecond(); 
    }

    @Override
    public Object convertDataValueToObjectValue(Object dataValue, Session session) {
        String str = (String) dataValue;
        if (str == null || str.isEmpty()) {
            return null; 
        }
        String[] parts = str.split(SEPARATOR);
        Integer key = Integer.parseInt(parts[0]);
        Integer value = Integer.parseInt(parts[1]);
        return new Pair(key, value); 
    }

    // Initialisation (optionnelle)
    @Override
    public void initialize(DatabaseMapping mapping, Session session) {
        
    }

    // Indique si l'objet est mutable
    @Override
    public boolean isMutable() {
        return true; // Nous disons ici que l'objet peut être modifié après sa conversion
    }
}