package student_mgmt_sys;

public class StudentBuilder extends Student{
	
	private int studentId = Main.db.getRowCount();
	private String firstName = null;
	private String lastName = null;
	private String dateOfBirth = null;
	private String gender = null;
	private String email = null;
	private String profession = null;
	private int credits = -1;
	private int graduateYear = -1;
	
	public StudentBuilder() { }
	
	public Student buildStudent() {
		return new Student(this.studentId,
						   this.firstName,
						   this.lastName,
						   this.dateOfBirth,
						   this.gender,
						   this.email,
						   this.profession,
						   this.credits,
						   this.graduateYear);
	}
	
	public StudentBuilder firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public StudentBuilder lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public StudentBuilder dateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}
	
	public StudentBuilder gender(String gender) {
		this.gender = gender;
		return this;
	}
	
	public StudentBuilder email(String email) {
		this.email = email;
		return this;
	}
	
	public StudentBuilder profession(String profession) {
		this.profession = profession;
		return this;
	}
	
	public StudentBuilder credits(int credits) {
		this.credits = credits;
		return this;
	}
	
	public StudentBuilder graduateYear(int graduateYear) {
		this.graduateYear = graduateYear;
		return this;
	}
}
