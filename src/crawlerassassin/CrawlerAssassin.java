package crawlerassassin;
import crawlerassassin.persistence.Reader;
import crawlerassassin.domain.Source;
import crawlerassassin.dommanipulation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import crawlerassassin.dommanipulation.PageParser;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 *
 * @author Michael Takrama - TVDS Team
 */
public class CrawlerAssassin {
    
    private static final String rawFilePath = new File("").getAbsolutePath().concat("\\rawdata\\");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<Source> sources = Reader.getDataSources();
        URL website;
        
        try {
            System.out.println("Currently started ...");
            
            for ( Source a : sources)
            {
                System.out.println("Currently crawling " + a.getFileName());
                website = new URL(a.getBaseUrl());
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(a.getFileName());
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                System.out.println("Crawling of " + a.getFileName() + " done.\n");
            }   
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlerAssassin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlerAssassin.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
        try {
            System.out.println("Processing Started");
           
            PageParser ocp = new JurisdictionCodeParser(new File(sources.get(3).getFileName()), sources.get(3).getBaseUrl());
            ocp.extractContentToFile();
            
            ocp = new AddressCodeParser(new File(sources.get(4).getFileName()), sources.get(4).getBaseUrl());
            ocp.extractContentToFile();
            
            ocp = new ResidentialParser(new File(sources.get(5).getFileName()), sources.get(5).getBaseUrl());
            ocp.extractContentToFile();
            
            ocp = new DetachedFields(new File(sources.get(6).getFileName()), sources.get(6).getBaseUrl());
            ocp.extractContentToFile();
            
            ocp = new CommercialOccupancy(new File(sources.get(7).getFileName()), sources.get(7).getBaseUrl());
            ocp.extractContentToFile();
            
            ocp = new CommercialParser(new File(sources.get(8).getFileName()), sources.get(8).getBaseUrl());
            ocp.extractContentToFile();
            
            System.out.println("Processing Ended");
            
            System.out.println("Completed ........... :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :)");
            
            } catch (IOException ex) {
                Logger.getLogger(CrawlerAssassin.class.getName()).log(Level.SEVERE, null, ex);
            }     
    }   
}
