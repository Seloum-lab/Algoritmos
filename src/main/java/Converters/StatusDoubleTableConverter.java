/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converters;

import Metier.Modele.Client;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author DeLL
 */
@Converter(autoApply = true)
public class StatusDoubleTableConverter implements AttributeConverter<Metier.Modele.Client.Status[][], String> {
    private static final String TABLESEPARATOR = "; ";
    private static final String SEPARATOR = ", ";
    
    @Override
    public String convertToDatabaseColumn(Client.Status[][] doubleTable) {
        String result = "";
        assert(doubleTable.length == 7);
        for (Metier.Modele.Client.Status[] table : doubleTable) {
            assert(table.length == 12);
            for (Metier.Modele.Client.Status status : table) {
                result += status.name() + SEPARATOR;
            }
            result = result.substring(0, result.length()-2);
            result += TABLESEPARATOR;
        }
        return result.substring(0, result.length()-2);
    }

    @Override
    public Client.Status[][] convertToEntityAttribute(String dbDoubleTable) {
        Client.Status[][] result = new Client.Status[7][12];
        if (dbDoubleTable == null || dbDoubleTable.isEmpty())
            return null;
        
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

   
    
}
