/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author DeLL
 */
@Converter(autoApply = true)
public class BooleanDoubleTableConverter implements AttributeConverter<boolean[][], String>{
    private static final String TABLESEPARATOR = "; ";
    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(boolean[][] doubleTable) {
        StringBuilder result = new StringBuilder();
        assert(doubleTable.length == 7);

        for (boolean[] table : doubleTable) {
            assert(table.length == 12);
            for (boolean dispo : table) {
                result.append(dispo ? "t" : "f").append(SEPARATOR);
            }
            result.setLength(result.length() - SEPARATOR.length());
            result.append(TABLESEPARATOR);
        }
        

        result.setLength(result.length() - TABLESEPARATOR.length());
        return result.toString();
    }

    @Override
    public boolean[][] convertToEntityAttribute(String dbDoubleTable) {
        boolean[][] result = new boolean[7][12];
        if (dbDoubleTable == null || dbDoubleTable.isEmpty())
            return null;
        
        String[] doubleTable = dbDoubleTable.split(TABLESEPARATOR);
        assert(doubleTable.length == 7);
        
        for (int i = 0; i<7; i++) {
            String[] table = doubleTable[i].split(SEPARATOR);
            assert(table.length == 12);
            for (int j = 0; j<12; j++) {
                result[i][j] = ("t".equals(table[j]));
            }
        }
        
        return result;
    }
    
}
