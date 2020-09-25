package student_mgmt_sys;

public class Main {

	public static Database db;
	
	public static void main(String[] args) {
		try {
			db = new Database("root", "root");
			Student student = new StudentBuilder().buildStudent();
			Student s2 = new Student(0, "root", "root", "1999-09-20", "f", "root@gmail.com", "cs", 102, 2021);
			db.printData(0);
			db.printData(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
