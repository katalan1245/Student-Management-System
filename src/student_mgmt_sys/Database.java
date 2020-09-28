package student_mgmt_sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class Database {

	private Connection connection;
	private Statement statement;
	private String sql = "";
	private ResultSet res = null;
	
	public Database(String dbName, String user, String password) throws SQLException{
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	public String getSql() {
		return this.sql;
	}
	
	public void addStudent(Student student) {
		String info = student.toSql();
		sql = String.format("INSERT INTO students VALUES (%s)", info);
		System.out.println("MySQL>> " + sql);
		try {
			statement.execute(sql);
		} catch(SQLException e) {
			Main.printError(e);
		}
	}
	
	public int getRowCount() {
		sql = "SELECT COUNT(*) AS total FROM students";
		try {
			res = statement.executeQuery(sql);
			res.next();
			return res.getInt("total") + 1;
		} catch(SQLException e) {
			Main.printError(e);
		}
		return -1;
	}
	
	public void deleteStudent(int studentId) {
		sql = "DELETE FROM students WHERE studentId = " + studentId;
		System.out.println("MySQL>> " + sql);
		try {
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			Main.printError(e);
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
							  "graduateYear = %s WHERE studentId = %s",
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
			Main.printError(e);
		}
	}
	
	public LinkedHashMap<String, String> getStudentData(int studentId) {
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		if(isDbEmpty())
			return null;
		sql = "SELECT * FROM students WHERE studentId = " + studentId;
		try {
			res = statement.executeQuery(sql);
			res.next();
			data.put("studentId", String.valueOf(res.getInt("studentId")));
			data.put("firstName", res.getString("firstName"));
			data.put("lastName", res.getString("lastName"));
			data.put("dateOfBirth", res.getString("dateOfBirth"));
			data.put("gender", res.getString("gender"));
			data.put("email", res.getString("email"));
			data.put("profession", res.getString("profession"));
			data.put("credits", String.valueOf(res.getInt("credits")));
			data.put("graduateYear", String.valueOf(res.getInt("graduateYear")));
		} catch(SQLException e) {
			Main.printError(e);
		}
		return data;
	}
	
	public LinkedHashMap<Integer, LinkedHashMap<String, String>> getTable() {
		LinkedHashMap<Integer, LinkedHashMap<String, String>> data = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();
		for(int i=1;i<getRowCount();i++) { 
			data.put(i,getStudentData(i));
		}
		return data;
	}
	
	public void printData(int studentId) {
		LinkedHashMap<String, String> row = getStudentData(studentId);
		for (String key : row.keySet()) {
			System.out.print(key.toString() + ": " + row.get(key) + "\t");
		}
		System.out.println();
	}
	
	public void clearDatabase() {
		sql = "DELETE FROM students";
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			Main.printError(e);
		}
	}
	
	public boolean isDbEmpty() {
		sql = "SELECT count(*) FROM students";
		int count = 0;
		try {
			res = statement.executeQuery(sql);
			while(res.next())
				count++;
			return count == 0;
		} catch(SQLException e) {
			Main.printError(e);
		}
		return true;
	}
	
	public void executeCommand(String command) {
		sql = command;
		System.out.println(sql);
		try {
			statement.execute(sql);
		} catch(SQLException e) {
			Main.printError(e);
		}
	}
	
	public boolean checkSystemLogin(String user, String pass) {
		sql = "SELECT * FROM users";
		try {
			res = statement.executeQuery(sql);
			while(res.next()) {
				String dbUser = res.getString("username");
				String dbPassword =res.getString("hashedPassword");
				if(dbUser.equals(user) && dbPassword.equals(pass))
					return true;
			}
		} catch (SQLException e) {
			Main.printError(e);
		}
		return false;
	}
}