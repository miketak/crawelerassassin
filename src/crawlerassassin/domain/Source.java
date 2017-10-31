/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlerassassin.domain;

import java.io.File;

/**
 * Data Transfer object for raw data sources.
 * @author Michael Takrama - TVDS Team
 */
public class Source
{
    private final String rawFilePath = new File("").getAbsolutePath().concat("\\rawdata\\");
    
    /**
     * Name assigned to source.
     */
    private String source; 

    /**
     * Type of source
     */
    public SourceType type;
    
    /**
     * Base url of source
     */
    private String baseUrl;

    /**
     * Constructor that initializes source, type and base url.
     * @param source Name identifier for the source
     * @param type Indicates the file type of the source
     * @param baseUrl Fully qualified url to raw data source
     */
    public Source(String source, SourceType type, String baseUrl){
        this.source = source;
        this.type = type;
        this.baseUrl = baseUrl;
    }
    
    /**
     * Getter for File name
     * @return file name with type extension
     */
    public String getFileName()
    {
        System.out.println( source + "." + type.toString() );
        return rawFilePath + source + "." + type.toString();
    }
    
    /**
     * Getter for the base URL
     * @return Base URL string
     */
    public String getBaseUrl()
    {
        return baseUrl;
    }

}
