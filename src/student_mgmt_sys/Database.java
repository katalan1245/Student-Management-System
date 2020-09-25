package student_mgmt_sys;

import java.sql.*;

public class Database {

	private Connection connection;
	private Statement statement;
	private String sql = "";
	private ResultSet res = null;
	
	public Database(String user, String password) throws Exception{
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	public void addStudent(Student student) {
		String info = student.toSql();
		sql = String.format("INSERT INTO students VALUES (%s)", info);
		System.out.println(sql);
		try {
			statement.execute(sql);
		} catch(Exception e) {
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void deleteStudent(int id) {
		sql = "DELETE FROM students WHERE id = " + id;
		System.out.println(sql);
		try {
			statement.executeUpdate(sql);
		} catch(Exception e) {
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
