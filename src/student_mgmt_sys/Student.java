package student_mgmt_sys;

public class Student {
	
	private int studentId = -1;
	private String firstName = null;
	private String lastName = null;
	private String dateOfBirth = null;
	private String gender = null;
	private String email = null;
	private String profession = null;
	private int credits = -1;
	private int graduateYear = -1;
	
	public Student() { }

	protected Student(int studentId,
				   String firstName,
				   String lastName,
				   String dateOfBirth,
				   String gender,
				   String email,
				   String profession,
				   int credits,
				   int graduateYear) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.email = email;
		this.profession = profession;
		this.credits = credits;
		this.graduateYear = graduateYear;
	}

	public String toSql() {
		return String.format("%s, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", %s, %s",
														   this.studentId,
														   this.firstName,
														   this.lastName,
														   this.dateOfBirth,
														   this.gender,
														   this.email,
														   this.profession,
														   this.credits,
														   this.graduateYear);
	}

	public int getId() {
		return this.studentId;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getDateOfBirth() {
		return this.dateOfBirth;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getProfession() {
		return this.profession;
	}
	
	public int getCredits() {
		return this.credits;
	}
	
	public int getGraduateYear() {
		return this.graduateYear;
	}
}
