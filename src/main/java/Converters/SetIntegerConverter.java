/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;

import static java.lang.Integer.max;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author DeLL
 */

@Converter(autoApply = true)
public class SetIntegerConverter implements AttributeConverter<Set<Integer>, String>{
    private static final String SEPARATOR = ", ";
    
    @Override
    public String convertToDatabaseColumn(Set<Integer> set) {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (Integer element : set) {
            stringBuilder.append(element).append(SEPARATOR);
        }
        stringBuilder.setLength(max(stringBuilder.length() - SEPARATOR.length(), 0));
        return stringBuilder.toString();
    }

    @Override
    public Set<Integer> convertToEntityAttribute(String dbSet) {
        Set<Integer> result = new HashSet();
        if (dbSet == null || dbSet.isEmpty()) {
            return null;
        }
        String[] ints = dbSet.split(SEPARATOR);
        for (String element : ints) {
            result.add(Integer.parseInt(element));
        }
        
        return result;
    }
    
}
