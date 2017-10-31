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
 * Page Parser for http://www.assess.co.polk.ia.us/web/inven/help/residence.html
 * @author Michael Takrama - TVDS Team
 */
public class ResidentialParser extends HtmlParser implements PageParser{
    
    private final String[] FILE_NAMES = {"Res_OccupancyCodes", "Res_ResidentType", "Res_Descriptions", "", "Res_Grade_Adjust", "Res_Roof_Material", "Res_Building_Style", "Res_Foundation", "Res_Basement_Qual", "Res_Heating", "Res_Condition", "Res_RoofType", "Res_Ext_WallType"};
    private Writer writer = new Writer();

    public ResidentialParser(File htmlFile, String baseUri) throws IOException {
        super(htmlFile, baseUri);
        numberOfTables = 14;
        hashValue = 1987615453;
        this.integrityCheck();
    }
    
    @Override
    public void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException 
    {
        for ( int i = 1 ; i < this.getTableCount() ; i++)
        {
            if ( i == 3)
                processDescriptionsTable();
            if ( i == 7 || i == 13)
                processBuildingStyle_ExtWallType(i);
            
            if ( i != 4 ){
                writer.persist(FILE_NAMES[i-1], tables.get(i).getContent());
            }
        }
    }
    
    private void processBuildingStyle_ExtWallType(int index) 
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
                sb2.append(part[2]).append(",").append(part[3]).append(EOF);
            }
            count++;
        }
        sb.append(sb2.toString());
        tables.set(index, new Table(tables.get(index).getName(), sb.toString()));
    }
    
    /**
     * Adds Heading Row to Descriptions Table
     */
    private void processDescriptionsTable()
    {
        int index = 3;
        StringBuilder sb = new StringBuilder();
        String[] lines = tables.get(index).getContent().split(EOF);
        
        int count = 0;
        for ( String line : lines)
        {
            if (count == 0)
                sb.append("\"Parameter\"").append(",").append("\"Description\"").append(EOF);
            
            sb.append(line).append(EOF);
            count++;
        }
        tables.set(index, new Table(tables.get(index).getName(), sb.toString()));
    }
      
}
