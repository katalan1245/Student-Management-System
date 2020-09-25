package student_mgmt_sys;

import java.sql.*;
import java.util.LinkedHashMap;

public class Database {

	private Connection connection;
	private Statement statement;
	private String sql = "";
	private ResultSet res = null;
	
	public Database(String user, String password) throws SQLException{
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	public void addStudent(Student student) {
		String info = student.toSql();
		sql = String.format("INSERT INTO students VALUES (%s)", info);
		System.out.println(sql);
		try {
			statement.execute(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getRowCount() {
		sql = "SELECT COUNT(*) AS total FROM students";
		System.out.println(sql);
		try {
			res = statement.executeQuery(sql);
			res.next();
			return res.getInt("total");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void deleteStudent(int id) {
		sql = "DELETE FROM students WHERE id = " + id;
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateStudent(Student student) {
		sql = String.format("UPDATE students SET firstName = \"%s\", " +
							  "lastName = \"%s\", " +
							  "dateOfBirth = \"%s\", " +
							  "gender = \"%s\", " +
							  "email = \"%s\", " +
							  "profession = \"%s\", " +
							  "credits = %s, " +
							  "graduateYear = %s WHERE id = %s",
							  student.getFirstName(),
							  student.getLastName(),
							  student.getDateOfBirth(),
							  student.getGender(),
							  student.getEmail(),
							  student.getProfession(),
							  student.getCredits(),
							  student.getGraduateYear(),
							  student.getId());
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedHashMap<String, String> getData(int id) {
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		sql = "SELECT * FROM students WHERE id = " + id;
		System.out.println(sql);
		try {
			res = statement.executeQuery(sql);
			res.next();
			data.put("id", String.valueOf(res.getString("id")));
			data.put("firstName", res.getString("firstName"));
			data.put("lastName", res.getString("lastName"));
			data.put("dateOfBirth", res.getString("dateOfBirth"));
			data.put("gender", res.getString("gender"));
			data.put("email", res.getString("email"));
			data.put("profession", res.getString("profession"));
			data.put("credits", String.valueOf(res.getInt("credits")));
			data.put("graduateYear", String.valueOf(res.getString("graduateYear")));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public LinkedHashMap<Integer, LinkedHashMap<String, String>> getTable() {
		LinkedHashMap<Integer, LinkedHashMap<String, String>> data = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();
		for(int i=1;i<getRowCount();i++) { 
			data.put(i,getData(i-1));
		}
		return data;
	}
	
	public void printData(int id) {
		LinkedHashMap<String, String> row = getData(id);
		for (String key : row.keySet()) {
			System.out.print(key.toString() + ": " + row.get(key) + "\t");
		}
		System.out.println();
	}
}