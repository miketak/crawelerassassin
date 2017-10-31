/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlerassassin.dommanipulation;

import crawlerassassin.domain.Table;
import crawlerassassin.persistence.Writer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Page Parser for http://www.assess.co.polk.ia.us/web/help/tutorials/exports/addresscode.html
 * @author Michael Takrama - TVDS Team
 */
public class AddressCodeParser extends HtmlParser implements PageParser{
    
    private ArrayList<Table> addressTables = new ArrayList<>();
    private static final String[] TABLE_NAMES = { "dir", "suffix", "suffix_dir", "unit_type"};
    final String heading = "\"Parameter\",\"Description\"";
    private Writer writer = new Writer();
    
    /**
     * Initializes super constructor and performs signature check
     * @param htmlFile
     * @param baseUri
     * @throws IOException 
     */
    public AddressCodeParser(File htmlFile, String baseUri) throws IOException {
        super(htmlFile, baseUri);
        numberOfTables = 2;
        hashValue = -1612985667;
        this.integrityCheck();
    }
    
    @Override
    public void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        parseTableData();
        for ( Table t : addressTables)
            writer.persist("Addr_" + t.getName(), t.getContent());    
    }
    
    /**
     * Breaks up table data into Table objects.
     */
    private void parseTableData()
    {
        String[] lines  = tables.get(1).getContent().split(EOF);
        int[] splitPoints = {4,11,14,38,41,48,51,62};
        int lineNumber = 1;
        StringBuilder sb = new StringBuilder();
        
        for ( String line : lines)
        {
            if ( lineNumber >= splitPoints[0] && lineNumber <= splitPoints[1] )
                sb = constructTables(line, lineNumber, splitPoints[0], splitPoints[1], sb, addressTables, TABLE_NAMES[0]);
            if ( lineNumber >= splitPoints[2] && lineNumber <= splitPoints[3] )
                sb = constructTables(line, lineNumber, splitPoints[2], splitPoints[3], sb, addressTables, TABLE_NAMES[1]);
            if ( lineNumber >= splitPoints[4] && lineNumber <= splitPoints[5] )
                sb = constructTables(line, lineNumber, splitPoints[4], splitPoints[5], sb, addressTables, TABLE_NAMES[2]);        
            if ( lineNumber >= splitPoints[6] && lineNumber <= splitPoints[7] )
                sb = constructTables(line, lineNumber, splitPoints[6], splitPoints[7], sb, addressTables, TABLE_NAMES[3]); 
            lineNumber++;
        }  
    }

    /**
     * Converts table from a 8 column table to a 2 column tables
     * @param line
     * @param lineNumber
     * @param startingIndex
     * @param endingIndex
     * @param sb
     * @param addressTables
     * @param tableName
     * @return 
     */
    private StringBuilder constructTables(String line, int lineNumber, int startingIndex, int endingIndex, StringBuilder sb, ArrayList<Table> addressTables, String tableName) {
        String[] lineData;
        lineData = line.split(",");
        if ( lineNumber == startingIndex) {sb.append(heading).append(EOF); }
        sb.append(lineData[1])
                .append(",")
                .append(lineData[2])
                .append(EOF);
        if ( lineNumber == endingIndex ){
            addressTables.add(new Table(tableName, sb.toString()));
            sb = new StringBuilder();
        }
        return sb;
    }
    
    
    
    
    
}
