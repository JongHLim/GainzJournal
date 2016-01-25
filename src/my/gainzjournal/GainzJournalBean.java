/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.gainzjournal;

/**
 *
 * @author jhl2298
 */

import java.sql.*;
import javax.sql.rowset.JdbcRowSet;
import com.sun.rowset.JdbcRowSetImpl;
import my.gainzjournal.datastructures.*;

public class GainzJournalBean {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Workouts";
    static final String DB_USER = "jhlim84";
    static final String DB_PASS = "9S3y8n4!";
    private JdbcRowSet rowSet = null;
    
    public GainzJournalBean() {
        try {
            Class.forName(JDBC_DRIVER);
            rowSet = new JdbcRowSetImpl();
            rowSet.setUrl(DB_URL);
            rowSet.setUsername(DB_USER);
            rowSet.setPassword(DB_PASS);
            rowSet.setCommand("SELECT * FROM Workout");
            rowSet.execute();
        }
        catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public Workout create(Workout w) {
        // ***** FINISH OTHERS *****
        try {
            rowSet.moveToInsertRow();
            rowSet.updateInt("workoutId", w.getWorkoutId());
            rowSet.updateString("date", w.getDate());
            rowSet.updateString("workoutType", w.getWorkoutType());
            // ***** FINISH OTHERS *****
            rowSet.insertRow();
            rowSet.moveToCurrentRow();
        } catch (SQLException ex) {
            try {
            rowSet.rollback();
            w = null;
         } catch (SQLException e) {

         }
         ex.printStackTrace();
      }
      return w;
   }
}
