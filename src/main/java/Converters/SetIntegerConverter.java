/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;

import static java.lang.Integer.max;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;


/**
 *
 * @author DeLL
 */


public class SetIntegerConverter implements Converter{
    private static final String SEPARATOR = ",";
    
    @Override
    public Object convertObjectValueToDataValue(Object objectValue, Session session) {
        if (objectValue == null) {
            return null;
        }
        
        Set<Integer> set = (Set<Integer>) objectValue;
        StringBuilder stringBuilder = new StringBuilder();
        
        for (Integer element : set) {
            stringBuilder.append(element).append(SEPARATOR);
        }
        stringBuilder.setLength(max(stringBuilder.length() - SEPARATOR.length(), 0));
        return stringBuilder.toString();
    }

    @Override
    public Object convertDataValueToObjectValue(Object dataValue, Session session) {
        if (dataValue == null || ( (String) dataValue).isEmpty()) {
            return null;
        }
        Set<Integer> result = new HashSet();
        String dbSet = (String) dataValue;
        String[] ints = dbSet.split(SEPARATOR);
        for (String element : ints) {
            result.add(Integer.parseInt(element));
        }
        
        return result;
    }
    
    @Override
    public void initialize(DatabaseMapping mapping, Session session) {
        
    }

    // Indique si l'objet est mutable
    @Override
    public boolean isMutable() {
        return true; // Nous disons ici que l'objet peut être modifié après sa conversion
    }
    
}
