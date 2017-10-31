package crawlerassassin.persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Writer class
 * @author Michael Takrama - TVDS Team
 */
public class Writer {
    
    private final String filePath = new File("").getAbsolutePath().concat("\\processed\\");
    
    /**
     * Creates a CSV file to processed folder
     * @param fileName Filename
     * @param content Already prepared CSV data
     * @throws FileNotFoundException Thrown when file isn't found.
     * @throws UnsupportedEncodingException Thrown when encoding protocol isn't supported
     */
    public void persist(String fileName, String content) throws FileNotFoundException, UnsupportedEncodingException
    {
        fileName = filePath + fileName + ".csv";
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            writer.println(content);
        } 
    }
     
}
