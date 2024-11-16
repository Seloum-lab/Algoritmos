/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;


/**
 *
 * @author DeLL
 */
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

public class BooleanDoubleTableConverter implements Converter {
    
    private static final String TABLESEPARATOR = ";";
    private static final String SEPARATOR = ",";

    @Override
    public Object convertDataValueToObjectValue(Object dataValue, Session session) {
        // Conversion de String à boolean[][]
        if (dataValue == null || ((String) dataValue).isEmpty()) {
            return null;
        }
        
        String dbDoubleTable = (String) dataValue;
        boolean[][] result = new boolean[7][12];

        String[] doubleTable = dbDoubleTable.split(TABLESEPARATOR);
        assert(doubleTable.length == 7);

        for (int i = 0; i < 7; i++) {
            String[] table = doubleTable[i].split(SEPARATOR);
            assert(table.length == 12);
            for (int j = 0; j < 12; j++) {
                result[i][j] = ("t".equals(table[j]));
            }
        }

        return result;
    }

    @Override
    public Object convertObjectValueToDataValue(Object objectValue, Session session) {
        // Conversion de boolean[][] à String
        if (objectValue == null) {
            return null;
        }
        
        boolean[][] doubleTable = (boolean[][]) objectValue;
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
    public boolean isMutable() {
        return true;
    }

    @Override
    public void initialize(DatabaseMapping dm, Session sn) {
    }
}
