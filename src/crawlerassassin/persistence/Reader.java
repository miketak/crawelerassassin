package crawlerassassin.persistence;
import crawlerassassin.domain.SourceType;
import crawlerassassin.domain.Source;
import java.util.ArrayList;

/**
 * Reader class
 * @author Michael Takrama - TVDS Team
 */
public class Reader {
    
    /**
     * Returns all data sources
     * @return ArrayList of all data sources
     */
    public static ArrayList<Source> getDataSources()
    {
        return new ArrayList<Source>(){{
            add(new Source("polkResInvFull", SourceType.txt, "http://www.assess.co.polk.ia.us/web/exports/resA/inven/POLKCOUNTY.txt"));
            add(new Source("polkCommInvFull", SourceType.txt, "http://www.assess.co.polk.ia.us/web/exports/commA/inven/POLKCOUNTY.txt"));
            add(new Source("polkAgricInvFull", SourceType.txt, "http://www.assess.co.polk.ia.us/web/exports/agA/inven/POLKCOUNTY.txt"));
            add(new Source("jurisdictionCodes", SourceType.html, "http://www.assess.co.polk.ia.us/web/help/tutorials/exports/juriscode2.html"));
            add(new Source("addressCodes", SourceType.html, "http://www.assess.co.polk.ia.us/web/help/tutorials/exports/addresscode.html"));
            add(new Source("residentialLookups", SourceType.html, "http://www.assess.co.polk.ia.us/web/inven/help/residence.html"));
            add(new Source("detachedFields", SourceType.html, "http://www.assess.co.polk.ia.us/web/inven/help/detached.html"));
            add(new Source("commercialOccupancy", SourceType.html, "http://www.assess.co.polk.ia.us/web/help/tutorials/exports/occgroup.html"));
            add(new Source("commercialLookups", SourceType.html, "http://www.assess.co.polk.ia.us/web/inven/help/csection.html"));
        }};
    } 
}
