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
 * PageParser for http://www.assess.co.polk.ia.us/web/help/tutorials/exports/occgroup.html
 * @author Michael Takrama - TVDS Team
 */
public class CommercialOccupancy extends HtmlParser implements PageParser{
    
    private final String[] FILENAMES = {"Commercial_Occupancy_Groups"};
    private final String HEADING = "\"OccupancyGroup\",\"OccupancyCode\",\"Description\"" + EOF;
    private Writer writer = new Writer();
    
    public CommercialOccupancy(File htmlFile, String baseUri) throws IOException {
        super(htmlFile, baseUri);
        numberOfTables = 3;
        hashValue = 1216199456;
        this.integrityCheck();
    }
    
    @Override
    public void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        processCommercialGroupsTable();
        writer.persist(FILENAMES[0], tables.get(1).getContent());
    }
    
    private void processCommercialGroupsTable()
    {
        int index = 1;
        StringBuilder sb = new StringBuilder();
        String[] lines = tables.get(index).getContent().split(EOF);
        
        int count = 0;
        for ( String line : lines )
        {
            if ( count == 0)
                sb.append(HEADING);
            else
                sb.append(line).append(EOF);
            
            count++;
        }
        tables.set(index, new Table(tables.get(index).getName(), sb.toString()));
        
    }
    
    
}
