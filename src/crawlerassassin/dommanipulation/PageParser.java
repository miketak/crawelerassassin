/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlerassassin.dommanipulation;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Interface for all page parsers
 * @author Michael Takrama - TVDS Team
 */
public interface PageParser {
    
    /**
     * Checks for changes in file
     * @return 
     */
    boolean checkSignature();
    
    /**
     * Prints table elements to console.
     */
    void printTables();
    
    /**
     * Prints table heading elements to console
     */
    void printTableHeadings();
    
    /**
     * Extracts contents, parses and persists it into CSV files
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException 
     */
    void extractContentToFile() throws FileNotFoundException, UnsupportedEncodingException;
    
    
    
}
