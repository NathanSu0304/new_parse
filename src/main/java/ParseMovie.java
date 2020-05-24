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

public class ParseMovie extends DefaultHandler{

    List<BaseMv> store_mv;
    private String tempVal;
    private BaseMv temp_mv;
    private String director_name;
    private List<String> all_genre;

    public ParseMovie() {
        store_mv = new ArrayList<BaseMv>();
    }

    public void runExample() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        parseDocument();
    }

    private void parseDocument() {

        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            javax.xml.parsers.SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse("mains243.xml", this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if (qName.equalsIgnoreCase("film")) {
            //create a new instance of employee
            all_genre = new ArrayList<String>();
            temp_mv = new BaseMv();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("dirname")){
            director_name = tempVal;
        }
        else if (qName.equalsIgnoreCase("film")) {
            temp_mv.setDirectorName(director_name);
            if (all_genre.size() != 0){
                temp_mv.setGenre(all_genre);
                store_mv.add(temp_mv);
            }
        } else if (qName.equalsIgnoreCase("t")) {
            if(tempVal.contains(",")){
                temp_mv.setTitle(tempVal.split(",")[0]);
            }
            else{
                temp_mv.setTitle(tempVal);
            }

        } else if (qName.equalsIgnoreCase("year")) {
            try{
                temp_mv.setYear(Integer.parseInt(tempVal));
            }
            catch (Exception e){
            }

        } else if (qName.equalsIgnoreCase("cat")) {
            if(tempVal.trim().toLowerCase().equals("dram"))
                all_genre.add("Drama");
            else if(tempVal.trim().toLowerCase().equals("susp"))
                all_genre.add("Thriller");
            else if(tempVal.trim().toLowerCase().equals("romt"))
                all_genre.add("Romantic");
            else if(tempVal.trim().toLowerCase().equals("musc"))
                all_genre.add("Musical");
            else if(tempVal.trim().toLowerCase().equals("myst"))
                all_genre.add("Mystery");
            else if(tempVal.trim().toLowerCase().equals("comd"))
                all_genre.add("Comedy");
            else if(tempVal.trim().toLowerCase().equals("docu"))
                all_genre.add("Documentary");
            else if(tempVal.trim().toLowerCase().equals("advt"))
                all_genre.add("Adventure");
            else if(tempVal.trim().toLowerCase().equals("west"))
                all_genre.add("Western");
            else if(tempVal.trim().toLowerCase().equals("fant"))
                all_genre.add("Fantasy");
            else if(tempVal.trim().toLowerCase().equals("scfi"))
                all_genre.add("Sci-Fi");
            else if(tempVal.trim().toLowerCase().equals("cart"))
                all_genre.add("Cartoon");
            else if(tempVal.trim().toLowerCase().equals("horr"))
                all_genre.add("Horror");
            else if(tempVal.trim().toLowerCase().equals("biop"))
                all_genre.add("Biographical Picture");
            else if(tempVal.trim().toLowerCase().equals("hist"))
                all_genre.add("History");
            else if(tempVal.trim().toLowerCase().equals("epic"))
                all_genre.add("Epic");
            else if(tempVal.trim().toLowerCase().equals("cnrb"))
                all_genre.add("Cops and Robbers");
            else if(tempVal.trim().toLowerCase().equals("crim"))
                all_genre.add("Crime");
            else if(tempVal.trim().toLowerCase().equals("cond"))
                all_genre.add("Comedy");
            else if(tempVal.trim().toLowerCase().equals("romt comd")){
                all_genre.add("Romantic");
                all_genre.add("Comedy");
            }
            else if(tempVal.trim().toLowerCase().equals("noir"))
                all_genre.add("Black");
            else if(tempVal.trim().toLowerCase().equals("biog"))
                all_genre.add("Biography");
            else if(tempVal.trim().toLowerCase().equals("disa"))
                all_genre.add("Disaster");
            else if(tempVal.trim().toLowerCase().equals("west1"))
                all_genre.add("Western");
            else if(tempVal.trim().toLowerCase().equals("adctx"))
                all_genre.add("Adventure");
            else if(tempVal.trim().toLowerCase().equals("txx"))
                all_genre.add("Uncategorized");
            else if(tempVal.trim().toLowerCase().equals("camp"))
                all_genre.add("Now - Camp");
            else if(tempVal.trim().toLowerCase().equals("surl"))
                all_genre.add("Sureal");
            else if(tempVal.trim().toLowerCase().equals("porn"))
                all_genre.add("Pornography");
            else if(tempVal.trim().toLowerCase().equals("surreal"))
                all_genre.add("Sureal");
            else if(tempVal.trim().toLowerCase().equals("romtadvt")){
                all_genre.add("Romantic");
                all_genre.add("Adventure");
            }
            else if(tempVal.trim().toLowerCase().equals("ctxxx"))
                all_genre.add("Uncategorized");
            else if(tempVal.trim().toLowerCase().equals("stage musical"))
                all_genre.add("Musical");
            else if(tempVal.trim().toLowerCase().equals("muusc"))
                all_genre.add("Music");
            else if(tempVal.trim().toLowerCase().equals("draam"))
                all_genre.add("Drama");
            else if(tempVal.trim().toLowerCase().equals("ram"))
                all_genre.add("Drama");
            else if(tempVal.trim().toLowerCase().equals("ctcxx"))
                all_genre.add("Uncategorized");
            else if(tempVal.trim().toLowerCase().equals("dramn"))
                all_genre.add("Drama");
            else if(tempVal.trim().toLowerCase().equals("psych dram"))
                all_genre.add("Drama");
            else if(tempVal.trim().toLowerCase().equals("ctxx"))
                all_genre.add("Uncategorized");
            else if(tempVal.trim().toLowerCase().equals("comdx"))
                all_genre.add("Comedy");
            else if(tempVal.trim().toLowerCase().equals("muscl"))
                all_genre.add("Musical");
            else if(tempVal.trim().toLowerCase().equals("s.f."))
                all_genre.add("Sci-Fi");
            else if(tempVal.trim().toLowerCase().equals("fanth*"))
                all_genre.add("Fantsy");
            else if(tempVal.trim().toLowerCase().equals("musc"))
                all_genre.add("Music");
            else if(tempVal.trim().toLowerCase().equals("tv"))
                all_genre.add("Reality-TV");
            else if(tempVal.trim().toLowerCase().equals("biopp"))
                all_genre.add("Biographical Picture");
            else if(tempVal.trim().toLowerCase().equals("mystp"))
                all_genre.add("Mystery");
            else if(tempVal.trim().toLowerCase().equals("comd west")){
                all_genre.add("Comedy");
                all_genre.add("Western");
            }
            else if(tempVal.trim().toLowerCase().equals("dramd"))
                all_genre.add("Drama");
            else if(tempVal.trim().toLowerCase().equals("fant"))
                all_genre.add("Fantasy");
            else if(tempVal.trim().toLowerCase().equals("dram>"))
                all_genre.add("Drama");
            else if(tempVal.trim().toLowerCase().equals("ront"))
                all_genre.add("Romantic");
            else if(tempVal.trim().toLowerCase().equals("dist"))
                all_genre.add("Disaster");
            else if(tempVal.trim().toLowerCase().equals("hor"))
                all_genre.add("Horror");
            else if(tempVal.trim().toLowerCase().equals("biopx"))
                all_genre.add("Biographical Picture");
            else if(tempVal.trim().toLowerCase().equals("sxfi"))
                all_genre.add("Sci-Fi");
            else if(tempVal.trim().toLowerCase().equals("biopp"))
                all_genre.add("Biographical Picture");
            else if(tempVal.trim().toLowerCase().equals("ducu"))
                all_genre.add("Documentary");
            else if(tempVal.trim().toLowerCase().equals("romtx"))
                all_genre.add("Romantic");
            else if(tempVal.trim().toLowerCase().equals("cart"))
                all_genre.add("Cartoon");
            else if(tempVal.trim().toLowerCase().equals("axtn"))
                all_genre.add("Violence");
            else if(tempVal.trim().toLowerCase().equals("actn"))
                all_genre.add("Violence");
            else if(tempVal.trim().toLowerCase().equals("act"))
                all_genre.add("Violence");
            else if(tempVal.trim().toLowerCase().equals("porb"))
                all_genre.add("Pornography");
            else if(tempVal.trim().toLowerCase().equals("dram.actn")){
                all_genre.add("Drama");
                all_genre.add("Violence");
            }
            else if(tempVal.trim().toLowerCase().equals("romt. comd"))
                all_genre.add("Violence");
            else if(tempVal.trim().toLowerCase().equals("biob"))
                all_genre.add("Biographical Picture");
            else if(tempVal.trim().toLowerCase().equals("docu dram")){
                all_genre.add("Documentary");
                all_genre.add("Drama");
            }
            else if(tempVal.trim().toLowerCase().equals("romt dram")){
                all_genre.add("Romantic");
                all_genre.add("Drama");
            }
            else if(tempVal.trim().toLowerCase().equals("dram docu")){
                all_genre.add("Documentary");
                all_genre.add("Drama");
            }
            else if(tempVal.trim().toLowerCase().equals("romt actn")){
                all_genre.add("Romantic");
                all_genre.add("Violence");
            }
            else if(tempVal.trim().toLowerCase().equals("noir comd")){
                all_genre.add("Black");
                all_genre.add("Comedy");
            }
            else if(tempVal.trim().toLowerCase().equals("noir comd romt")){
                all_genre.add("Black");
                all_genre.add("Comedy");
                all_genre.add("Romantic");
            }
            else if(tempVal.trim().toLowerCase().equals("romt fant")){
                all_genre.add("Fantasy");
                all_genre.add("Romantic");
            }
            else if(!tempVal.equals("Surr") && !tempVal.equals("Avant Garde") && !tempVal.equals("CnRbb") && !tempVal.equals("Homo") && !tempVal.equals("CmR") && !tempVal.equals("Duco") &&
                    !tempVal.equals("H") && !tempVal.equals("RFP; H*") && !tempVal.equals("Natu") && !tempVal.equals("Scat") && !tempVal.equals("") && !tempVal.equals("CnR") &&
                    !tempVal.equals("H**") && !tempVal.equals("Bio") && !tempVal.equals("AvGa") && !tempVal.equals("H0") && !tempVal.equals("Weird") && !tempVal.equals("noir") &&
                    !tempVal.equals("Cult") && !tempVal.equals("Sctn") && !tempVal.equals("sports") && !tempVal.equals("Psyc") && !tempVal.equals("Adct") && !tempVal.equals("Viol") &&
                    !tempVal.equals("CA") && !tempVal.equals("Kinky") && !tempVal.equals("TVmini") && !tempVal.equals("Sati") && !tempVal.equals("Dicu") && !tempVal.equals("Comd Noir") &&
                    !tempVal.equals("Faml") && !tempVal.equals("UnDr") && !tempVal.equals("Expm") && !tempVal.equals("Art Video") && !tempVal.equals("Allegory") && !tempVal.equals("verite") &&
                    !tempVal.equals("anti-Dram") && !tempVal.equals("Road")){
                all_genre.add("tempVal");
            }
        }
    }

}
