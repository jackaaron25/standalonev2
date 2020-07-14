package rovpp.standalone2.hijacks_api.DAO;

import rovpp.standalone2.hijacks_api.Model.ModelDeployment;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.sql.ResultSet;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
@Repository
public class DeploymentServiceReal implements DeploymentDao{
    //private final JdbcTemplate jdbcTemplate;
    //@Autowired
    //public DeploymentServiceReal(JdbcTemplate jdbcTemplate){
    //    this.jdbcTemplate = jdbcTemplate;
    //}
    @Override
    public List<ModelDeployment> getHijacks(){
        Connection c = null;
      Statement stmt = null;
      try {
        Class.forName("org.postgresql.Driver");
        c = DriverManager
           .getConnection("jdbc:postgresql://localhost:5432/bgp",
           "postgres", "password");
        c.setAutoCommit(false);
        

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM deployment;" );
        int id;
        String country;
        int detected_as_path;
        int detected_by_bgpmon_peers;
        String detected_origin_name;
        int detected_origin_number;
        String start_time;
        String end_time;
        int event_number;
        String event_type;
        String expected_origin_name;
        int expected_origin_number;
        String expected_prefix;
        String more_specific_prefix;
        String url;
        List<ModelDeployment> mlist = new ArrayList<>();
        while ( rs.next() ) {
           id = rs.getInt("id");
           country = rs.getString("country");
           detected_as_path  = rs.getInt("detected_as_path");
           detected_by_bgpmon_peers = rs.getInt("detected_by_bgpmon_peers");
           detected_origin_name = rs.getString("detected_origin_name");
           detected_origin_number = rs.getInt("detected_origin_number");
           start_time = rs.getString("start_time");
           end_time = rs.getString("end_time");
           event_number = rs.getInt("event_number");
           event_type = rs.getString("event_type");
           expected_origin_name = rs.getString("expected_origin_name");
           expected_origin_number = rs.getInt("expected_origin_number");
           expected_prefix = rs.getString("expected_prefix");
           more_specific_prefix = rs.getString("more_specific_prefix");
           url = rs.getString("url");
           ModelDeployment curModel = new ModelDeployment(id, country, detected_as_path, detected_by_bgpmon_peers, detected_origin_name, detected_origin_number, start_time, end_time, event_number, event_type, expected_origin_name, expected_origin_number, expected_prefix, more_specific_prefix, url);
            mlist.add(curModel);
        }
        rs.close();
        stmt.close();
        c.close();
        return mlist;
     } catch ( Exception e ) {
         
        System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        System.exit(0);
        return null;

     }
        
    }
    @Override
    public int activeHijacksSummary(){
        return 1;
    }
}