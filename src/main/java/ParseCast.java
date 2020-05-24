import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParseCast extends DefaultHandler {
    List<BaseCast> store_mv;
    private String tempVal;
    private BaseCast temp_mv;
    private String director;

    public ParseCast() {
        store_mv = new ArrayList<BaseCast>();
    }

    public void runExample() {
        parseDocument();
    }

    private void parseDocument() {

        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse("casts124.xml", this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Iterate through the list and print
     * the contents
     */
    private void printData() {

        System.out.println("No of Actor '" + store_mv.size() + "'.");

        Iterator<BaseCast> it = store_mv.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    //Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if(qName.equalsIgnoreCase("m")) {
            temp_mv = new BaseCast();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("is")) {
            director = tempVal;
        } else if (qName.equalsIgnoreCase("m")) {
            temp_mv.setDirector(director);
            store_mv.add(temp_mv);
        } else if (qName.equalsIgnoreCase("t")) {
            temp_mv.setTitle(tempVal);
        } else if (qName.equalsIgnoreCase("a")) {
            if(!tempVal.equalsIgnoreCase("s a")){
                temp_mv.setActor(tempVal);
            }
        }
    }
}
