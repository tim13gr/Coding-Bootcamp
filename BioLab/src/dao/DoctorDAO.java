package dao;

import java.sql.*;
import java.util.ArrayList;
import connection.DB;
import model.Doctor;


public class DoctorDAO extends UserDAO{

	public DoctorDAO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Queries database and returns all registered students in a ArrayList
	 * 
	 * @return ArrayList<Doctor>
	 * @throws Exception
	 */
	public ArrayList<Doctor> getDoctor() throws Exception {
		
		DB db = new DB();
		Connection con = null;
		//con = db.getConnection();
		ResultSet rs =null;
		Statement st = null;
		String sqlquery = "SELECT * FROM user JOIN doctor ON user.username=doctor.username;";
		ArrayList<Doctor> results= new ArrayList<Doctor>();
		
		try{
			
			db.open();
			con = db.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlquery);
			while(rs.next()){
				results.add(new Doctor(rs.getString("user.name"),rs.getString("user.surname"),rs.getString("user.username"),rs.getString("user.email"),rs.getString("user.password"),rs.getInt("doctor.doc_credential")));
			}
			
			rs.close();
			st.close();
		
			
			return results;
			
		}catch(Exception e){
			throw new Exception("An error occured while getting doctors from database: " + e.getMessage());
		}finally {
			
			try {
				db.close();
			} catch (Exception e) {
				
			} 
			
		}
		
	}//End of getDoctor

	/**
	 * Saves doctor in the database
	 * 
	 * @param doctor, Doctor
	 * @throws Exception
	 */
	public void saveDoctor(Doctor doctor) throws Exception {
		
		saveUser(doctor);
		

		DB db = new DB();
		Connection con = null;
		PreparedStatement st = null;
		String sqlq = "Insert into doctor(username,doc_credential) values(?,?);"; //leipei to onoma tis basis
		
		try{
			
			db.open();
			con=db.getConnection();
			st=con.prepareStatement(sqlq);
			st.setInt(2, doctor.getCredential());
			st.setString(1, doctor.getUsername());
			st.executeUpdate();
			st.close();
			db.close();
			
		}catch(Exception e){
			throw new Exception("An error occured while getting students from database: " + e.getMessage());
		}finally{
			try {
				db.close();
			} catch (Exception e) {
				
			} 
		}
	}
}
