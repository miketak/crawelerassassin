/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlerassassin.domain;

/**
 * Data transfer object for Tables extracted from HTML files
 * @author Michael Takrama - TVDS Team
 */
public class Table {
    
    private String name;
    private String content;
    
    /**
     * Constructor that initializes table name and content
     * @param name Name assigned to table
     * @param content  Text content within the table
     */
    public Table(String name, String content)
    {
       this.name = name;
       this.content = content;
    }
    
    /**
     * Getter for table name
     * @return The table name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Getter for table content
     * @return The table content
     */
    public String getContent()
    {
        return content;
    }
    
    /**
     * Setter for table content
     * @param content  Fully formatted table contents
     */
    public void setContent(String content)
    {
        this.content = content;
    }
    
    /**
     * Setter for table name
     * @param name Name identifier for table.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
}
