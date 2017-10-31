package crawlerassassin.dommanipulation;

import crawlerassassin.domain.Table;
import crawlerassassin.domain.TableHeading;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Parent class in charge of automatically populating document variable for child classes
 * @author Michael Takrama - TVDS Team
 */
public class HtmlParser implements PageParser {
    
    /**
     * Number of tables in html file
     */
    int numberOfTables;
    
    /**
     * Hash value of html file
     */
    long hashValue;
    
    ArrayList<Table> tables;
    ArrayList<TableHeading> tableHeadings;
    
    private static int numRuns;
    protected Document document;
    
    private static final String CHARSETNAME = "UTF-8";
    private static final String LOGFILENAME = "crawl.log";
    protected static final String EOF = "\r\n";
    private static final String LOGFILEPATH = new File("").getAbsolutePath().concat("\\" + LOGFILENAME);
    public static final Logger logger = Logger.getLogger(LOGFILENAME);
    
    /**
     * Initializes document file for parsing.
     * @param htmlFile File object to be parsed
     * @param baseUri Base URI of File
     * @throws IOException Thrown on I/O errors encountered
     */
    public HtmlParser(File htmlFile, String baseUri) throws IOException
    {
        document = Jsoup.parse(htmlFile, CHARSETNAME, baseUri);
        configureLogger();
        extractTables();
        extractTableHeadings();
        numRuns++;
    }
    
    /**
     * Returns number of tables in html file
     * @return Integer representing number of table elements in file
     */
    protected int getTableCount() {
        Elements tableElements = document.getElementsByTag("table");
        int tableCount = 0;
        return tableElements.stream().map((_item) -> 1).reduce(tableCount, Integer::sum);
    }
    
    /**
     * Returns the number of Element in Elements type
     * @param elements
     * @return an integer 
     */
    protected int getNumberOfElements(Elements elements)
    {
        int count = 0;
        return  elements.stream().map((_item) -> 1).reduce(count, Integer::sum);
    }

    /**
     * Extracts table data from HTML file into instance variable tables
     */
    private void extractTables() {
        logger.log(Level.INFO, "{0}: Tables extraction start", document.baseUri());
        ArrayList<Table> tableList = new ArrayList<>();
        StringBuilder sb = null;
        
        // Print table Content
        int tableCount = 0;
        for ( Element tElement : document.select("table"))
        {
            tableCount++;
            //Print rows;
            sb = new StringBuilder();
            for ( Element row : tElement.select("tr"))
            {
                //Print row data
                int rowDataCount = 0;
                for ( Element t : row.select("td"))
                {
                    rowDataCount++;
                    sb.append("\"").append(removeUnicodeChars(t.text())).append("\"");
                    if ( rowDataCount != this.getNumberOfElements(row.select("td")))
                        sb.append(",");
                    else
                        sb.append("");
                }
                sb.append(EOF);
            }
            tableList.add(new Table(Integer.toString(tableCount), sb.toString()));
        }
        this.tables = tableList;
        logger.log(Level.INFO, "{0}: Table extraction successful", document.baseUri());
    }
    
    private String removeUnicodeChars(String text)
    {
        return text.replace("\u00A0", "");
    }
    

    /**
     * Extracts all table headings in HTML file to tableHeadings instance variable
     */
    private void extractTableHeadings() {
        ArrayList<TableHeading> theadings = new ArrayList<>();
        StringBuilder sb;
        
        // Print table Headings
        int theadCount = 0;
        for ( Element table : document.select("table"))
        {
            sb = new StringBuilder();
            theadCount++;
            int index = 0;
            for ( Element th : table.select("th"))
            {
                index++;
                sb.append(th.text());
                if ( this.getNumberOfElements(table.select("th")) != index)
                    sb.append(",");
                else
                    sb.append(EOF);
            }
            
            if ( sb.length() > 0)
                theadings.add(new TableHeading(Integer.toString(theadCount), sb.toString()));
        }
        this.tableHeadings = theadings;
    }
    
    /**
     * Returns tables from html file
     * @return  ArrayList<Table>
     */
    protected ArrayList<Table> getTables()
    {
        return this.tables;
    }
    
    /**
     * Returns list table headings from html file
     * @return ArrayList<TableHeadings>
     */
    protected ArrayList<TableHeading> getTableHeadings()
    {
        return this.tableHeadings;
    }
    
    /**
     * Checks for any changes in file content by hash.
     * @return boolean true - no changes, false - changes detected
     */
    @Override
    public boolean checkSignature() 
    {
        long hashVal = document.text().hashCode();
        return (numberOfTables == this.getTableCount()) && this.hashValue == hashVal;
    }

    /**
     * Method for extracting table content from ArrayList<Table> object.
     * @throws FileNotFoundException thrown when file isn't found
     * @throws UnsupportedEncodingException  thrown when encoding protocol isn't supported.
     */
    @Override
    public void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
     /**
     * Prints table elements to console - for troubleshooting purposes
     */
    @Override
    public void printTables()
    {
        System.out.println("Printing all table contents from the mama");
        for ( Table t : tables)
        {
            System.out.println(t.getName());
            System.out.println(t.getContent());
        }
        System.out.println("End of all table contents.");
    }
    
    /**
     * Prints table heading elements to console - mainly for troubleshooting
     */
    @Override
    public void printTableHeadings()
    {
        System.out.println("Priting all table headings contents");
        for ( TableHeading th : tableHeadings)
        {
            System.out.println(th.getName());
            System.out.println(th.getContent());
        }
        System.out.println("End of table heading contents");
    }
    
    /**
     * Configures logger only once for log entries
     */
    private void configureLogger()
    {
        if ( numRuns != 0)
            return;
        
        FileHandler fh;
        try {
            fh = new FileHandler(LOGFILEPATH);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(HtmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void integrityCheck()
    {
        logger.log(Level.INFO, "{0}: Signature check: {1}", new Object[]{document.baseUri(), checkSignature()});
        if ( !checkSignature() )
        {
            logger.log(Level.INFO, "{0}: Signature check: {0}.Aborting ....", new Object[]{document.baseUri(), checkSignature()});
        }
    }
       
}
