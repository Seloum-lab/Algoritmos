/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;

import Utils.Pair;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;




/**
 *
 * @author DeLL
 */
@Converter(autoApply = true)
public class PairConverterOld implements AttributeConverter<Pair<Integer, Integer>, String>{
    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(Pair<Integer, Integer> pair) {
        if (pair == null)
            return null;
        return Integer.toString(pair.getFirst()) + SEPARATOR + Integer.toString(pair.getSecond());
    }

    @Override
    public Pair<Integer, Integer> convertToEntityAttribute(String dbPair) {
        Pair <Integer, Integer> pair;
        if (dbPair == null || dbPair.isEmpty())
            return null;
        String[] pieces = dbPair.split(SEPARATOR);
        pair = new Pair(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]));
        return pair;
    }
    
}
