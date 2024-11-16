/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;

import Metier.Modele.Client;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;


/**
 *
 * @author DeLL
 */

public class StatusDoubleTableConverter implements Converter{
    private static final String TABLESEPARATOR = ";";
    private static final String SEPARATOR = ",";
    
    @Override
    public String convertObjectValueToDataValue(Object objectValue, Session session) {
        if (objectValue == null) {
            return null;
        }
        
        Client.Status[][] doubleTable = (Client.Status[][]) objectValue;
        StringBuilder result = new StringBuilder();
        assert(doubleTable.length == 7);

        for (Metier.Modele.Client.Status[] table : doubleTable) {
            assert(table.length == 12);
            for (Metier.Modele.Client.Status status : table) {
                result.append(status.name()).append(SEPARATOR);
            }
            result.setLength(result.length() - SEPARATOR.length());
            result.append(TABLESEPARATOR);
        }
        

        result.setLength(result.length() - TABLESEPARATOR.length());
        return result.toString();
    }
    
    @Override
    public Object convertDataValueToObjectValue(Object dataValue, Session session) {
        if (dataValue == null || ((String) dataValue).isEmpty())
            return null;
        
        Client.Status[][] result = new Client.Status[7][12];
        String dbDoubleTable = (String) dataValue;
        
        
        String[] doubleTable = dbDoubleTable.split(TABLESEPARATOR);
        assert(doubleTable.length == 7);
        
        for (int i = 0; i<7; i++) {
            String[] table = doubleTable[i].split(SEPARATOR);
            assert(table.length == 12);
            for (int j = 0; j<12; j++) {
                result[i][j] = Client.Status.valueOf(table[j]);
            }
        }
        
        
        
        return result;
    }

    @Override
    public boolean isMutable() {
        return true;
    }
    
    @Override
    public void initialize(DatabaseMapping dm, Session sn) {
    }

       
}
