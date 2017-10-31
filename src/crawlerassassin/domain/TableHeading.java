/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlerassassin.domain;

/**
 * Data transfer objects for extracted table headings
 * @author Michael Takrama - TVDS Team
 */
public class TableHeading {
    
    private String name;
    private String content;
    
    /**
     * Initializes table heading name and content.
     * @param name Name assigned to extracted table heading
     * @param content  Table Heading content from the HTML file.
     */
    public TableHeading(String name, String content)
    {
        this.name = name;
        this.content = content;
    }
    
    /**
     * Getter for assigned table heading name
     * @return  Table heading name
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Returns table heading content
     * @return  Table heading content
     */
    public String getContent()
    {
        return this.content;
    }
    
}
