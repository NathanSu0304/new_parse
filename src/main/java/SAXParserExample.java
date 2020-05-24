import java.io.*;
import java.sql.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXParserExample extends DefaultHandler {
    static List<BaseActor> store_mv;
    private String tempVal;
    private BaseActor temp_mv;
    private String director;
    static long actor_id;
    static long film_id;


    public SAXParserExample() {
        store_mv = new ArrayList<BaseActor>();
    }

    public void runExample() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        parseDocument();
//        printData();
    }

    private void parseDocument() {

        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse("actors63.xml", this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    //Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if(qName.equalsIgnoreCase("actor")) {
            temp_mv = new BaseActor();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("actor")) {
            store_mv.add(temp_mv);
        } else if (qName.equalsIgnoreCase("stagename")) {
            temp_mv.setName(tempVal);
        } else if (qName.equalsIgnoreCase("dob")) {
            try{
                String temp = tempVal.trim();
                int my_year = Integer.parseInt(temp);
                temp_mv.setDob((my_year + ""));
            }
            catch(Exception e){
                temp_mv.setDob("null");
            }
        }
    }

    public static void writeFile(String name, String birthYear) throws IOException {
        actor_id += 1;
        FileWriter fr = new FileWriter("actor.txt",true);
        fr.write("nm" + actor_id + ",");
        fr.write(name +",");
        fr.write(birthYear + "\n");
        fr.close();
    }

    public static void writeMovieFile(long film_id, String title,int year,String directorname) throws IOException {
        FileWriter fr = new FileWriter("movie.txt",true);

        fr.write("tt"+ film_id + ",");
        fr.write(title +",");
        fr.write(year + ",");
        fr.write(directorname + "\n");

        fr.close();

    }

    public static void writeGIMFile(long film_id, int genre_id) throws IOException {
        FileWriter fr = new FileWriter("gim.txt",true);

        fr.write(genre_id +",");
        fr.write("tt"+ film_id + "\n");
        fr.close();

    }
    public static void writeGenreFile(int id, String name) throws IOException {
        FileWriter fr = new FileWriter("genre.txt",true);

        fr.write(id +",");
        fr.write(name + "\n");
        fr.close();
    }


    public static void writeRelationFile(String starid, String movieid) throws IOException {
        FileWriter fr = new FileWriter("relation.txt",true);

        fr.write(starid +",");
        fr.write(movieid + "\n");
        fr.close();
    }

    public static HashMap<String, BaseMv>parse_mv_Txt(String fileName) throws IOException{
        File f = new File(fileName);
        HashMap<String,BaseMv>mv_res = new HashMap<String, BaseMv>();
        BufferedReader br = new BufferedReader(new FileReader(f));

        String temp[];
        String st;
        String id, title,director;
        int year;

        while ((st = br.readLine()) != null) {
            temp = st.split(",");

            id = temp[0];
            title = temp[1];
            year = Integer.parseInt(temp[2]);
            director = temp[3];
            mv_res.put(id, new BaseMv(title, year, director));
        }

        return mv_res;
    }




    public static HashMap<String, BaseActor>parseTxt(String fileName) throws IOException {
        File f = new File(fileName);
        HashMap<String, BaseActor>res = new HashMap<String, BaseActor>();
        BufferedReader br = new BufferedReader(new FileReader(f));

        String temp[];

        String st;
        String id, name, year;

        while ((st = br.readLine()) != null) {
            temp = st.split(",");
            id = temp[0];
            name = temp[1];
            year = temp[2];
            res.put(id, new BaseActor(name, year));
        }

        return res;
    }




    public static void batchInsert(String fileName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException, SQLException {

        Connection conn = null;

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String jdbcURL="jdbc:mysql://localhost:3306/moviedb";

        try {
            conn = DriverManager.getConnection(jdbcURL,"mytestuser", "mypassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement psInsertRecord=null;
        String sqlInsertRecord=null;

        int[] iNoRows=null;

        if(fileName.equals("actor.txt")){
            HashMap<String, BaseActor> insertActor = new HashMap<String, BaseActor>();
            insertActor = parseTxt("actor.txt");

            sqlInsertRecord="insert into stars (id, name, birthYear) values(?,?,?)";
            try {
                assert conn != null;
                conn.setAutoCommit(false);

                psInsertRecord=conn.prepareStatement(sqlInsertRecord);
                String temp;


                for(String key: insertActor.keySet()) {
                    psInsertRecord.setString(1, key);
                    psInsertRecord.setString(2,insertActor.get(key).getName());
                    temp = insertActor.get(key).getDob().trim();
                    if(temp.equals("null")){
                        psInsertRecord.setNull(3,java.sql.Types.INTEGER);
                    }
                    else{
                        psInsertRecord.setInt(3,Integer.parseInt(temp));
                    }

                    psInsertRecord.addBatch();

                }

                iNoRows=psInsertRecord.executeBatch();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(fileName.equals("movie.txt")){
            HashMap<String, BaseMv> insertMv = new HashMap<String, BaseMv>();
            insertMv = parse_mv_Txt("movie.txt");

            sqlInsertRecord="insert into movies (id, title,year,director) values(?,?,?,?)";
            try {
                assert conn != null;
                conn.setAutoCommit(false);
                psInsertRecord=conn.prepareStatement(sqlInsertRecord);

                for(String key: insertMv.keySet()) {
                    psInsertRecord.setString(1, key);
                    psInsertRecord.setString(2,insertMv.get(key).getTitle());
                    psInsertRecord.setString(3,insertMv.get(key).getYear() + "");
                    psInsertRecord.setString(4,insertMv.get(key).getDirectorName());
                    psInsertRecord.addBatch();
                }
                iNoRows=psInsertRecord.executeBatch();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(fileName.equals("gim.txt")){
            ArrayList<ArrayList<String>> insertGim = new ArrayList<ArrayList<String>>();

            File f = new File("gim.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String temp[];

            String st;
            String gen_id, mv_id;

            while ((st = br.readLine()) != null) {
                temp = st.split(",");
                gen_id = temp[0];
                mv_id = temp[1];
                ArrayList<String> new_insert = new ArrayList<String>();
                new_insert.add(gen_id);
                new_insert.add(mv_id);
                insertGim.add(new_insert);
            }

            sqlInsertRecord="insert into genres_in_movies (genreId, movie) values(?,?)";
            try {
                assert conn != null;
                conn.setAutoCommit(false);

                psInsertRecord=conn.prepareStatement(sqlInsertRecord);

                for (ArrayList<String>ss: insertGim){
                    psInsertRecord.setString(1,ss.get(0));
                    psInsertRecord.setString(2,ss.get(1));
                    psInsertRecord.addBatch();
                }

                iNoRows=psInsertRecord.executeBatch();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(fileName.equals("genre.txt")){
            HashMap<Integer, String> insertGen = new HashMap<Integer, String>();

            File f = new File("genre.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String temp[];

            String st;
            String id, name;

            while ((st = br.readLine()) != null) {
                temp = st.split(",");
                id = temp[0];
                name = temp[1];
                insertGen.put(Integer.parseInt(id), name);
            }

            sqlInsertRecord="insert into genres (id, name) values(?,?)";
            try {
                assert conn != null;
                conn.setAutoCommit(false);

                psInsertRecord=conn.prepareStatement(sqlInsertRecord);

                for(Integer key: insertGen.keySet()) {
                    psInsertRecord.setInt(1, key);
                    psInsertRecord.setString(2,insertGen.get(key));
                    psInsertRecord.addBatch();

            }

                iNoRows=psInsertRecord.executeBatch();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else if(fileName.equals("relation.txt")){
            ArrayList<ArrayList<String>>insertSim = new ArrayList<ArrayList<String>>();
            File f = new File("relation.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String temp[];

            String st;
            String starId, movieId;

            while ((st = br.readLine()) != null) {
                temp = st.split(",");
                starId = temp[0];
                movieId = temp[1];
                ArrayList<String> at = new ArrayList<String>();
                at.add(starId);
                at.add(movieId);
                insertSim.add(at);
            }

            sqlInsertRecord="insert into stars_in_movies (starId, movieId) values(?,?)";
            try {
                conn.setAutoCommit(false);

                psInsertRecord=conn.prepareStatement(sqlInsertRecord);

                for(ArrayList<String>ss: insertSim){
                    psInsertRecord.setString(1,ss.get(0));
                    psInsertRecord.setString(2,ss.get(1));
                    psInsertRecord.addBatch();
                }
                iNoRows=psInsertRecord.executeBatch();
                conn.commit();


            } catch (SQLException e) {
                e.printStackTrace();
            }
//
        }

        try {
            if(psInsertRecord!=null) psInsertRecord.close();
            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        List<BaseActor> Actor_filter = new ArrayList<BaseActor>();
        List<BaseMv> Mv_filter = new ArrayList<BaseMv>();

        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String jdbcURL="jdbc:mysql://localhost:3306/moviedb?useSSL=false";

        try {
            conn = DriverManager.getConnection(jdbcURL,"mytestuser", "mypassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert conn != null;

       String max_id = "";
       Statement statement = conn.createStatement();

       String query = "select max(id) as id from stars";
       ResultSet rs = statement.executeQuery(query);
       if(rs.next()){
           max_id =  rs.getString("id");
       }
       actor_id = Long.parseLong(max_id.substring(2));


       Statement state = conn.createStatement();
       String query1 = "select name,birthYear from stars";
       ResultSet rs1 = state.executeQuery(query1);
       while(rs1.next()){
           BaseActor actor = new BaseActor();
           actor.setName(rs1.getString("name"));
           actor.setDob(rs1.getString("birthYear"));
           Actor_filter.add(actor);
       }

       SAXParserExample spe = new SAXParserExample();
       spe.runExample();

       Iterator<BaseActor> it = store_mv.iterator();
       while (it.hasNext()) {
           BaseActor i = it.next();
           if(!Actor_filter.contains(i)){
               writeFile(i.getName(),i.getDob());
           }
       }

       batchInsert("actor.txt");
//
////======================================
//
       ArrayList<String> all_gen = new ArrayList<String>();
       String query2 = "select name from genres";
       Statement my_statement = conn.createStatement();
       ResultSet rs3 = my_statement.executeQuery(query2);

       while(rs3.next()){
           all_gen.add(rs3.getString("name"));
       }
       int all_gen_size = all_gen.size();


       Statement state1 = conn.createStatement();
       String query3 = "select title, year, director from movies";
       ResultSet rs2 = state1.executeQuery(query3);
       while(rs2.next()){
           BaseMv mv = new BaseMv();
           mv.setTitle(rs2.getString("title"));
           mv.setYear(rs2.getInt("year"));
           mv.setDirectorName(rs2.getString("director"));
           Mv_filter.add(mv);
       }

       Statement statement5 = conn.createStatement();
       String query_mv_id = "select max(id) as id from movies";
       ResultSet rs_mv_id = statement5.executeQuery(query_mv_id);
       if(rs_mv_id.next()){
           max_id =  rs_mv_id.getString("id");
       }

       film_id = Long.parseLong(max_id.substring(2));

       ParseMovie parseMovie = new ParseMovie();
       parseMovie.runExample();
       Iterator<BaseMv> it1 = parseMovie.store_mv.iterator();


       while (it1.hasNext()) {
           BaseMv i = it1.next();
           BaseMv i2 = new BaseMv();
           i2.setDirectorName(i.getDirectorName());
           i2.setYear(i.getYear());
           i2.setTitle(i.getTitle());
           List<String> gen_list = i.getGenre();

           if(!Mv_filter.contains(i2)){
               writeMovieFile((++film_id), i.getTitle(),i.getYear(),i.getDirectorName());

               Iterator<String> each_gen = gen_list.iterator();
               while(each_gen.hasNext()){
                   String each_gen_str = each_gen.next();

                   if(all_gen.contains(each_gen_str)){
                       writeGIMFile(film_id, (all_gen.indexOf(each_gen_str)+1));
                   }
                   else{
                       all_gen.add(each_gen_str);
                       writeGenreFile((++all_gen_size),each_gen_str);
                       writeGIMFile(film_id, all_gen_size);

                   }
               }
           }
       }
       batchInsert("movie.txt");
       batchInsert("genre.txt");
        batchInsert("gim.txt");


//////====================================

        List<String> After_Mv = new ArrayList<String>();
        List<String> After_Actor = new ArrayList<String>();
        List<BaseStarMovie> Mv_Star = new ArrayList<BaseStarMovie>();

        ParseCast parseCast = new ParseCast();
        parseCast.runExample();
        Iterator<BaseCast> it3 = parseCast.store_mv.iterator();

        HashMap<String, String> map=new HashMap<String, String>();
        HashMap<String, String> map1=new HashMap<String, String>();


        Statement state3 = conn.createStatement();
        String query4 = "select id, title from movies";
        ResultSet rs4 = state3.executeQuery(query4);

        while(rs4.next()){
            map.put(rs4.getString("title"),rs4.getString("id"));
            After_Mv.add(rs4.getString("title"));
        }

        Statement state4 = conn.createStatement();
        String query5 = "select id, name from stars";
        ResultSet rs5 = state4.executeQuery(query5);
        while(rs5.next()){
            map1.put(rs5.getString("name"),rs5.getString("id"));
            After_Actor.add(rs5.getString("name"));
        }

        Statement state5 = conn.createStatement();
        String query6 = "select starId, movieId from stars_in_movies";
        ResultSet rs6 = state5.executeQuery(query6);
        while(rs6.next()){
            BaseStarMovie new_one = new BaseStarMovie();
            new_one.setMVid(rs6.getString("movieId"));
            new_one.setStarid(rs6.getString("starId"));
            Mv_Star.add(new_one);
        }

        while(it3.hasNext()){
            BaseCast relation = it3.next();
            if(After_Actor.contains(relation.getActor()) && After_Mv.contains(relation.getTitle())){
                BaseStarMovie check = new BaseStarMovie();
                check.setMVid(map.get(relation.getTitle()));
                check.setStarid(map1.get(relation.getActor()));
                if(!Mv_Star.contains(check)) {
                    writeRelationFile(map1.get(relation.getActor()), map.get(relation.getTitle()));
                    Mv_Star.add(check);
                }
            }
        }
        batchInsert("relation.txt");
        conn.close();
    }
}