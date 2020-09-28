package student_mgmt_sys;

import java.awt.EventQueue;
import java.sql.SQLException;

public class Main {

	public static Database db;
	public static boolean login = false;
	
	public static void main(String[] args) {
		
		new Login();
		while(!login) { }
		//startSystem();
	}
	
	public static void startSystem() {
		connectDB();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
				} catch (Exception e) {
					printError(e);
				}
			}
		});
	}
	
	private static void connectDB() {
		try {
			db = new Database("students", "root", "root");
			System.out.println("MySQL>> Connected succesfully to: 'root'");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void printError(Exception e) {
		System.out.println("Error>>\n-------------------------------------------------------------------------------------------------------------------------");
		if(e instanceof SQLException)
			System.out.println("SQL Command: " + db.getSql());
		e.printStackTrace();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
	}
}
