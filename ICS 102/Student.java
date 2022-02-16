
public class Student {
	private int studentID;
	private String name;
	private double[] grade;
	
	public Student(int studentID, String name, double[] grade) {
		this.studentID = studentID;
		this.name = name;
		this.grade = grade.clone();
	}
	
	public Student(int studentID) {
		this.studentID = studentID;
	}
	
	

	

	public int getStudentID() {
		return studentID;
	}
	
	public String getName() {
		return name;
	}
	
	public double[] getGrades() {
		return grade;
	}
	
	
	public void addGrade(double grade) throws IllegalArgumentException{
		if(grade < 0 || grade > 100) {
			throw new IllegalArgumentException ("Error: Invaild Quiz Grade, Please Re-Enter grade");
		}
		else {
			this.grade[this.grade.length - 1] = grade;
		}
	}
	
	public double[] increaseGradeArraySize() {
		double[] gradeNew = this.grade.clone();
		this.grade = new double [this.grade.length + 1];
		for(int i = 0 ; i < gradeNew.length ; i++) {
			this.grade[i] = gradeNew[i];
		}
		return this.grade;
	}
	
	public void modifyGrade(double grade, int quizNum ) throws IllegalArgumentException{
		if(quizNum > getGrades().length || quizNum < 0	)
			throw new IllegalArgumentException("Error: Invaild quiz number");
			
		
		else if(grade < 0 || grade > 100)
			throw new IllegalArgumentException("Error: Invaild Quiz Grade");
		else {
			this.grade[quizNum - 1] = grade;
		}
	}
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if (this.getClass() != obj.getClass())
			return false;
		else {
			Student objStudent = (Student) obj;
			return objStudent.studentID == this.studentID;
		}
	}
	
	public String toString() {
		String grades = "";
		String quizNumbers = "";
		for(int i = 0 ; i < grade.length ; i++) {
			grades += grade[i] + "   ";
		}
		for(int i = 1 ; i <= grade.length ; i++) {
			quizNumbers += "Quiz" + i + "   ";
		}
		return String.format("StudentID \t Student Name \t %s %n %d    \t %-15.15s  %-15.30s%n",quizNumbers, studentID,name,grades);
	}
	
	public String toString1() {
		String grades = "";
		String quizNumbers = "";
		for(int i = 0 ; i < grade.length ; i++) {
			grades += grade[i] + "   ";
		}
		for(int i = 1 ; i <= grade.length ; i++) {
			quizNumbers += "Quiz" + i + "   ";
		}
		return String.format(" %d  \t  %-15.15s  %-15.30s", studentID,name,grades);
	}



}
