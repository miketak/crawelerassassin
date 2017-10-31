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
 * Page Parser for http://www.assess.co.polk.ia.us/web/inven/help/csection.html
 * @author Michael Takrama - TVDS Team
 */
public class CommercialParser extends HtmlParser implements PageParser{
    
    private final String[] FILE_NAMES = {"SectionFields", "Occupancy", "Foundation", "FrontType", 
        "FrameType", "Comm_ExteriorWall", "Comm_Roof", "Pass_Elev_QL", 
        "Roof_Material", "Skywalk", "Entrnc_Qual", "Landing_Qual", 
        "Building_Class", "Cover_Qual", "Condition", "Frt+Elv_Cap", "Life_Expect"};
            
    private Writer writer = new Writer();

    public CommercialParser(File htmlFile, String baseUri) throws IOException {
        super(htmlFile, baseUri);
        numberOfTables = 25;
        hashValue = -1709453434;
        this.integrityCheck();
    }
    
    @Override
    public void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException 
    {
        cleanseTableData();
        attachTableNames();

        for ( int i = 0 ; i < tables.size() ; i++)
        {
    
            if ( i == 0)
                processDescriptionsTable(0);
            if ( i == 1 )
                processsOccupancyCodes(1);
            if ( i > 1 ){
                writer.persist("Comm_" + tables.get(i).getName(), tables.get(i).getContent());
            }
        }
    }
    
    private void cleanseTableData()
    {
        tables.remove(0);
        tables.remove(2);
        
        for (int i = 0; i < tables.size(); i++) {
            if ( tables.get(i).getContent().split(EOF).length < 2 )
                tables.remove(i);
        }
    }
    
    private void attachTableNames()
    {
        for (int i = 0; i < tables.size(); i++) {
            if ( i > 1)
                tables.get(i).setName(FILE_NAMES[i]);
            
        }
    }
    
    private void processsOccupancyCodes(int index) 
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String[] lines = tables.get(index).getContent().split(EOF);
        
        int count = 0;
        for ( String line : lines )
        {
            
            String[] part = line.split(",");
            
            if ( count == 0 )
                sb.append(part[0]).append(",").append(part[1]).append(EOF);   
            else
            {
                sb.append(part[0]).append(",").append(part[1]).append(EOF);
                sb2.append(part[3]).append(",").append(part[4]).append(EOF);
            }
            count++;
        }
        sb.append(sb2.toString());
        tables.set(index, new Table(FILE_NAMES[1], sb.toString()));
    }
    
    /**
     * Adds Heading Row to Descriptions Table
     */
    private void processDescriptionsTable(int index)
    {
        StringBuilder sb = new StringBuilder();
        String heading = "\"Parameter\",\"Description\"";
        String[] lines = tables.get(index).getContent().split(EOF);
        
        int count = 0;
        for ( String line : lines)
        {
            if (count == 0)
                sb.append(heading).append(EOF);
            
            sb.append(line).append(EOF);
            count++;
        }
        tables.set(index, new Table(FILE_NAMES[0], sb.toString()));
    }
      
}
