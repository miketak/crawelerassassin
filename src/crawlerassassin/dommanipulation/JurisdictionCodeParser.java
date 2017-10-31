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
 * Page Parser for http://www.assess.co.polk.ia.us/web/help/tutorials/exports/juriscode2.html
 * @author Michael Takrama
 */
public class JurisdictionCodeParser extends HtmlParser implements PageParser{
    
    private final String[] FILE_NAMES = {"Jurisdiction_Codes"};
    private Writer writer = new Writer();
    
    public JurisdictionCodeParser(File htmlFile, String baseUri) throws IOException {
        super(htmlFile, baseUri);
        numberOfTables = 3;
        hashValue = -1794912509;
        this.integrityCheck();
    }
    
    @Override
    public void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        processJurisdictionTable();
        writer.persist(FILE_NAMES[0], tables.get(1).getContent());
    }
    
    private void processJurisdictionTable()
    {
        int index = 1;
        StringBuilder sb = new StringBuilder();
        String[] lines = tables.get(index).getContent().split(EOF);
        
        int count = 0;
        for ( String line : lines )
        {
            if ( count == 0)
                sb.append("\"District #\"")
                    .append(",")
                    .append("\"Nbhd\"")
                    .append(",")
                    .append("\"School District\"")
                    .append(EOF);
            else
                sb.append(line).append(EOF);
            
            count++;
        }
        tables.set(index, new Table(tables.get(index).getName(), sb.toString()));
        
    }
    
    
}
