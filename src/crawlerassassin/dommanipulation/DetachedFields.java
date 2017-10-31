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

/**
 * Page Parser for http://www.assess.co.polk.ia.us/web/inven/help/detached.html 
 * @author Michael Takrama - TVDS Team
 */
public class DetachedFields extends HtmlParser implements PageParser{
    
    private final String[] TABLE_NAMES = { "occupancy_codes", "func_desc", "condition", "constr_type", "meas_code", "field_descriptions"};
    private final String HEADING = "\"Code\",\"Term\"";
    private final String FILE_PREFIX = "Detached_";
    private Writer writer = new Writer();
    
    public DetachedFields(File htmlFile, String baseUri) throws IOException {
        super(htmlFile, baseUri);
        numberOfTables = 8;
        hashValue = -1130815119;
        this.integrityCheck();
    }
    
    @Override
    public void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        int count = 0;
        for ( Table t : tables)
        {
            if ( count == 1)
            {
                proccessOccupancyCodes(t);
                writer.persist(FILE_PREFIX + tables.get(1).getName(), tables.get(1).getContent()); 
            }
            if ( count > 2 ){
                writer.persist(FILE_PREFIX + TABLE_NAMES[count-2], t.getContent());
            }   
            count++;
        }        
    }
    
    private void proccessOccupancyCodes(Table t) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        
        String[] lines = t.getContent().split(EOF);
        
        int lineNumber = 1;
        String[] ld = null;
        for( String line : lines)
        {
            ld = line.split(",");
            if ( lineNumber == 1)
            {
                sb.append(HEADING).append(EOF);
                sb3.append(ld[6]).append(",").append(ld[7]).append(EOF);
            }
            else
            {
                sb.append(ld[0]).append(",").append(ld[1]).append(EOF);
                sb2.append(ld[3]).append(",").append(ld[4]).append(EOF);
                sb3.append(ld[6]).append(",").append(ld[7]).append(EOF);  
            }
            lineNumber++;
        }
        sb.append(sb2).append(sb3);
        
        this.tables.set(1, new Table(TABLE_NAMES[0], sb.toString() ));
        
        
    }  
}
