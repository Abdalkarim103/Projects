import java.util.*;
import java.io.*;

public class StudentDriver {

	public static void main(String[] args) throws IOException {
		Scanner kb = new Scanner(System.in);

		// gets the number of students and creates an array of objects
		int numberOfStudent = 0, numberOfQuizzes1 = 0;
		numberOfStudent = numberOfStudents();
		numberOfQuizzes1 = numberOfQuizzes();
		Student[] students = new Student[numberOfStudent];

		students = readFile(students);

		int menu = 0;

		do {
			System.out.println(
					"1. Display Grade Info for all students\r\n" + "2. Display Grade Info for a particular student\r\n"
							+ "3. Display quiz averages for all students\r\n"
							+ "4. Modify a particular quiz grade for a particular student\r\n"
							+ "5. Add quiz grades for a particular quiz for all students\r\n" + "6. Add New Student\r\n"
							+ "7. Delete Student\r\n" + "8. Exit\r\n" + "Please select your choice:");
			try {
				menu = menuChoice();
			} catch (InputMismatchException e) {
				break;
			}

			switch (menu) {
			case 1:
				displayGradesAll(students);
				break;
			case 2:
				displayGrades(students);
				break;
			case 3:
				quizAverage(students, numberOfQuizzes1);
				break;
			case 4:
				modifyQuiz(students, numberOfQuizzes1);
				break;

			case 5:
				addQuizGradeAll(students, numberOfQuizzes1);
				break;

			case 6:
				students = addNewStudent(students, numberOfQuizzes1);
				break;
			case 7:
				students = deleteStudent(students);
				break;
			case 8:
				System.out.println("Thank you for using our program!");
				saveFiles(students);
				break;
			default:
				System.out.println("Invaild choice: please choose again");
				break;
			}

		} while (menu != 8);

	}

	public static int menuChoice() {
		int menu = 0;
		try {
			Scanner kb = new Scanner(System.in);
			menu = kb.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(e);
		}
		return menu;
	}

	public static Student[] readFile(Student[] students) throws IOException {
		FileInputStream instream = new FileInputStream("Students.txt");
		Scanner kb = new Scanner(System.in);
		Scanner fis = new Scanner(instream);

		int StudentID, j = 0, k = 0;
		String line, firstName, lastName, fullName = "";
		double[] grades;

		Scanner fis2 = new Scanner(instream);
		while (fis2.hasNextDouble()) {
			line = fis2.nextLine();
			Scanner SS = new Scanner(line);
			StudentID = SS.nextInt();
			firstName = SS.next();
			lastName = SS.next();
			fullName = firstName + " " + lastName;

			int numberOfQuizzes = 0;

			while (SS.hasNextDouble()) {
				SS.nextDouble();
				numberOfQuizzes++;
			}

			k++;
			grades = new double[numberOfQuizzes];
			int i = 0;

			Scanner SQ = new Scanner(line);
			SQ.nextInt();
			SQ.next();
			SQ.next();
			while (SQ.hasNextDouble()) {
				grades[i] = SQ.nextDouble();
				i++;

			}
			students[j] = new Student(StudentID, fullName, grades);

			j++;
		}
		return students;
	}

	// gives the user a pause between options
	public static void pressEnter() {
		Scanner kb = new Scanner(System.in);
		System.out.println("Press Enter key to continue . . .");
		kb.nextLine();
	}

	// counts and return the number of students in the file
	public static int numberOfStudents() throws IOException {
		FileInputStream instream = new FileInputStream("Students.txt");
		Scanner fis = new Scanner(instream);
		int numberOfStudent = 0;
		while (fis.hasNext()) {
			fis.nextLine();
			numberOfStudent++;

		}
		return numberOfStudent;
	}

	// counts and return the number of quizzes taken for the students in the file
	public static int numberOfQuizzes() throws IOException {
		FileInputStream instream = new FileInputStream("Students.txt");
		Scanner fis = new Scanner(instream);
		int numberOfQuizzes = 0;
		String line;
		line = fis.nextLine();
		Scanner fos = new Scanner(line);
		fos.nextInt();
		fos.next();
		fos.next();
		while (fos.hasNextDouble()) {
			fos.nextDouble();
			numberOfQuizzes++;
		}
		return numberOfQuizzes;
	}

	// display the information for all students along with their quizzes grades by
	// reading from the array
	public static void displayGradesAll(Student[] students) throws IOException {
		FileInputStream instream = new FileInputStream("Students.txt");
		Scanner fis = new Scanner(instream);

		System.out.print("StudentID \t Student Name \t  ");
		int numberOfQuizzes = 0;
		for (int i = 0; i < students.length; i++) {
			if (students[i].getGrades().length > numberOfQuizzes)
				numberOfQuizzes = students[i].getGrades().length;
		}
		for (int i = 1; i <= numberOfQuizzes; i++) {
			System.out.print("Quiz" + i + "   ");
		}
		System.out.println();

		for (int i = 0; i < students.length; i++) {
			System.out.println(students[i].toString1());
		}
		fis.close();
		pressEnter();
	}

	// display information and quizzes for one student by entering his ID
	public static void displayGrades(Student[] students) {
		Scanner kb = new Scanner(System.in);
		int ID = 0;
		System.out.println("Enter StudenID: ");
		try {
			ID = kb.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(e);
			kb.hasNextLine();
		}
		Student studentSearch = new Student(ID);
		// studentSearch.setIDSearch(ID);
		boolean check = false;

		for (int i = 0; i < students.length; i++) {
			if (students[i].equals(studentSearch)) {
				double[] grades = students[i].getGrades();
				System.out.println(students[i].toString());

				check = true;
				break;
			}
		}
		if (!check)
			System.out.println("Error: Invalid student ID");
		pressEnter();
	}

	// the average of quizzes for each student
	public static void quizAverage(Student[] students, int numberOfQuizzes) {
		double sum;
		double[] average = new double[students.length];
		numberOfQuizzes = students[1].getGrades().length;
		System.out.println("StudentID \tStudent Name \t   Average");
		for (int i = 0; i < students.length; i++) {
			sum = 0.0;
			double[] total = new double[students[i].getGrades().length];
			total = students[i].getGrades();
			for (int j = 0; j < total.length; j++) {
				sum += total[j];
			}
			average[i] = sum / numberOfQuizzes;
		}
		for (int k = 0; k < students.length; k++) {
			System.out.printf(" %d \t       %12.13s  \t  %.2f ", students[k].getStudentID(), students[k].getName(),
					average[k]);
			System.out.println();
		}

		pressEnter();
	}

	// changes the quiz grade for a student by using a set method in the class
	public static void modifyQuiz(Student[] students, int numberOfQuizzes) {
		Scanner kb = new Scanner(System.in);
		boolean found = false, error = false;
		int studentNumber = 0;
		int ID = 0, quizNumber = 0;
		double grade = 0.0;

		System.out.println("Please enter Student ID:");
		try {
			ID = kb.nextInt();
			System.out.println("Please enter quiz number to modify:");
			quizNumber = kb.nextInt();
			System.out.println("Please enter new quiz" + quizNumber + " grade:");
			grade = kb.nextDouble();
		} catch (InputMismatchException e) {
			System.out.println(e);
			error = true;
			kb.nextLine();
		}
		Student studentSearch = new Student(ID);
		// studentSearch.setIDSearch(ID);
		if (error) {
			System.out.println("Error: Invaild input");
			pressEnter();
		} else {
			if (quizNumber <= 0 || quizNumber > numberOfQuizzes) {
				System.out.println("Error: Invalid quiz number");
				pressEnter();
			} else if (grade < 0 || grade > 100) {
				System.out.println("Error: Invalid quiz grade");
				pressEnter();
			} else {

				for (int i = 0; i < students.length; i++) {
					if (students[i].equals(studentSearch)) {
						found = true;
						studentNumber = i;
						break;
					}
				}

				if (!found) {
					System.out.println("Error: Invalid Student ID");

				} else {
					try {
						System.out.println("Before grade modification: " + students[studentNumber].toString1());
						students[studentNumber].modifyGrade(grade, quizNumber);
						System.out.println("After garde modification: " + students[studentNumber].toString1());
					} catch (InputMismatchException e) {
						System.out.println(e);
						kb.nextLine();
					} catch (IllegalArgumentException e1) {
						System.out.println(e1);
					}
				}
				pressEnter();

			}
		}
	}

	// will add the new quiz grades for all students. up to 4 quizzes, will give an
	// error if more
	public static void addQuizGradeAll(Student[] students, int numberOfQuizzes) {
		Scanner kb = new Scanner(System.in);
		int count = students[1].getGrades().length;
		if (count >= 4) {
			System.out.println("Error: maximum number of quizzes exceeded");
			pressEnter();
		} else {
			boolean check = false;
			int indexError = 0;
			double[] newGrades = new double[students.length];

			// will increase the size of the array of grades for all students allowing the
			// program to add one quiz grade to each student
			for (int i = 0; i < students.length; i++) {
				students[i].increaseGradeArraySize();
			}

			System.out.println("Please enter quiz grades for Quiz#" + (count + 1));

			for (int i = 0; i < students.length; i++) {
				try {
					System.out.println("Please enter grade for student : " + students[i].getStudentID());
					students[i].addGrade(kb.nextDouble());

				} catch (InputMismatchException e) {
					System.out.println(e + " Error: Invaild input, please Re-Enter grade");
					kb.nextLine();
					i--;

				} catch (IllegalArgumentException e1) {
					System.out.println(e1);
					i--;

				}
			}
			pressEnter();

		}
	}

	// add a new student to the array of objects
	public static Student[] addNewStudent(Student[] students, int numberOfQuizzes) {
		Scanner kb = new Scanner(System.in);
		System.out.println("Please enter the ID of the student to be added: ");
		int ID = 0;
		boolean error = false;

		try {
			ID = kb.nextInt();
			error = false;
			if (ID < 0)
				error = true;
		} catch (InputMismatchException e) {
			System.out.println(e);
			error = true;
			kb.nextLine();
		}
		Student studentSearch = new Student(ID);
		// studentSearch.setIDSearch(ID);
		boolean check = false;
		for (int i = 0; i < students.length; i++) {
			if (students[i].equals(studentSearch)) {
				System.out.println("Student ID already exist!");
				check = true;
				break;
			}
		}

		if (error) {
			System.out.println("Invaild StudentID");
			pressEnter();
			return students;

		} else {

			if (check) {
				pressEnter();
				return students;

			} else {
				String name = null;
				double[] grade = new double[numberOfQuizzes];
				try {
					System.out.println("Enter student name");
					kb.nextLine();
					name = kb.nextLine();

				} catch (InputMismatchException e) {
					System.out.println(e);
					pressEnter();
				}

				if (!checkName(name)) {
					System.out.println("Error: Invaild name");
					pressEnter();
					return students;

				} else {

					for (int i = 0; i < numberOfQuizzes; i++) {

						try {
							System.out.println("Enter student's grade for quiz#" + (i + 1));
							grade[i] = kb.nextDouble();
							if (grade[i] < 0 || grade[i] > 100) {
								System.out.println("Error: Invaild grade please re-enter the grade");
								i--;
							}
						} catch (InputMismatchException e) {
							System.out.println("Error: Invaild grade please re-enter the grade");
							kb.nextLine();
							i--;
						}

					}
				}

				Student[] newStudent = students.clone();
				students = new Student[students.length + 1];
				for (int i = 0; i < newStudent.length; i++) {
					students[i] = newStudent[i];
				}
				students[students.length - 1] = new Student(ID, name, grade);
				pressEnter();
				return students;

			}
		}
	}

	// removes a student from the array of objects
	public static Student[] deleteStudent(Student[] students) {
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter student ID to be deleted: ");
		int ID = 0;
		boolean error = false;
		try {
			ID = kb.nextInt();
			error = false;
			if (ID < 0)
				error = true;
		} catch (InputMismatchException e) {
			System.out.println(e);
			error = true;
			kb.nextLine();
		}
		if (error) {
			System.out.println("Error: Invaild studentID");
			pressEnter();
			return students;
		} else {
			Student studentSearch = new Student(ID);
			// studentSearch.setIDSearch(ID);
			boolean check = false;
			int k = 0;
			Student[] studentsNew = new Student[students.length - 1];
			for (int i = 0; i < students.length; i++) {
				if (students[i].equals(studentSearch)) {
					k = i;
					check = true;
					break;
				}
			}
			if (!check) {
				System.out.println("StudentID not found");
				pressEnter();
				return students;
			} else {
				for (int i = 0; i < k; i++) {
					studentsNew[i] = students[i];

				}
				for (int i = (k + 1); i < (studentsNew.length + 1); i++) {
					studentsNew[i - 1] = students[i];
				}
				System.out.println("Student deleted");
				pressEnter();
				return studentsNew;
			}
		}
	}

	public static boolean checkName(String name) {
		name.replace(" ", "");
		for (int i = 0; i < name.length() - 1; i++) {
			if (Character.isDigit(name.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// after the user exit the program, this method will save all informations in
	// the file, so it can be read from again.
	public static void saveFiles(Student[] students) throws IOException {
		FileOutputStream outstream = new FileOutputStream("Students.txt");
		PrintWriter pw = new PrintWriter(outstream);

		pw.print("");

		for (int i = 0; i < students.length; i++) {
			pw.println(students[i].toString1());

		}

		pw.close();
	}

}
