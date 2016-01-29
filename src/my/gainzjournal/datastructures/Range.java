package my.gainzjournal.datastructures;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;

public class Range implements Predicate {

	private int workoutId;
	private String index;
	
	public Range(int workoutId, String index) {
		this.workoutId = workoutId;
		this.index = index;
	}

	@Override
	public boolean evaluate(RowSet rs) {
		// TODO Auto-generated method stub

        // Check the present row determine if it lies
        // within the filtering criteria.

        try {
            if (rs.getInt(index) == workoutId) {
            	return true;
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        
        return false;

	}

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean evaluate(Object value, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
