package student_mgmt_sys;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Actions {

	public static final int NUM_COLUMNS = 9;
	public static final int DATE_OF_BIRTH_COLUMN_INDEX = 3;
	public static final int ID_STUDENT = 0;
	public static final int FIRST_NAME = 1;
	public static final int LAST_NAME = 2;
	public static final int DATE_OF_BIRTH = 3;
	public static final int GENDER = 4;
	public static final int EMAIL = 5;
	public static final int PROFESSION = 6;
	public static final int CREDITS = 7;
	public static final int GRADUATE_YEAR = 8;
	
	public static void copySummaryToClipboard(JTextPane summary) {
		StringSelection stringSelection = new StringSelection(summary.getText());
		Clipboard clp = Toolkit.getDefaultToolkit().getSystemClipboard();
		clp.setContents(stringSelection, null);
		System.out.println("Actions>> Summary Copied To Clipboard.");
	}
	
	public static void writeTableToExcel(JTable table,String filename) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
			WritableSheet sheet = workbook.createSheet("Students", 0);
			
			Label[] labels = new Label[NUM_COLUMNS];
			for(int i=0;i<NUM_COLUMNS;i++) {
				labels[i] = new Label(i,0,table.getColumnName(i));
				sheet.addCell(labels[i]);
			}
			
			for(int i=0;i<table.getRowCount();i++) {
				for(int j=0;j<NUM_COLUMNS;j++) {
					if(j == DATE_OF_BIRTH_COLUMN_INDEX)
						labels[j] = new Label(j,i+1,dateFormat(table.getValueAt(i, j).toString()));
					else
						labels[j] = new Label(j,i+1,table.getValueAt(i, j).toString());
					sheet.addCell(labels[j]);
				}
			}
			workbook.write();
			workbook.close();
			System.out.println("Actions>> Wrote Successfully To " + filename + ".");
		} catch(Exception e) {
			Main.printError(e);
		}
	}
	
	public static String writeSummary(JTable table, int row) {
		if(table.getRowCount() == 0)
			return "Add Students To The Table";
		String summary = "";
		try {
		summary = "Summary\n" +
						 "------------------------------------------------------------\n" + 
						 "Student ID: " + table.getValueAt(row, 0).toString() + "\n" +
						 "First Name: " + table.getValueAt(row, 1).toString() + "\n" +
						 "Last Name: " + table.getValueAt(row, 2).toString() + "\n" +
						 "Date Of Birth: " + dateFormat(table.getValueAt(row, 3).toString()) + "\n" +
						 "Gender: " + table.getValueAt(row, 4).toString() + "\n" +
						 "Email: " + table.getValueAt(row, 5).toString() + "\n" +
						 "Profession: " + table.getValueAt(row, 6).toString() + "\n" +
						 "Credits: " + table.getValueAt(row, 7).toString() + "\n" +
						 "Graduate Year: " + table.getValueAt(row, 8).toString() + "\n" +
						 "------------------------------------------------------------\n";
		System.out.println("Actions>> Generated Summary.");
		} catch(Exception e) {
			Main.printError(e);
		}
		return summary;
	}
	
	public static String dateFormat(String date) {
		if(date == null)
			return "";
		StringBuilder formatDate = new StringBuilder();
		String[] tokens = date.split("-");
		for (String str : tokens) {
			formatDate.insert(0,str + "/");
		}
		return formatDate.substring(0, formatDate.length() - 1);
	}
	
	public static String toSqlDateFormat(String date) {
		if(date == null)
			return "";
		StringBuilder formatDate = new StringBuilder();
		String[] tokens = date.split("/");
		for (String str : tokens) {
			formatDate.insert(0, "-" + str);
		}
		return formatDate.substring(1, formatDate.length());
	}
	
	public static void clearDatabase() {
		Main.db.clearDatabase();
		System.out.println("Actions>> Table Cleared.");
	}
	
	public static void clearSummary(JTextPane summary) {
		summary.setText("Select Student From The Table:");
		System.out.println("Actions>> Summary Cleared.");
	}

	public static void createStudent(Student studentToAdd) {
		Main.db.addStudent(studentToAdd);
	}

	public static void sendCommand(String text) {
		Main.db.executeCommand(text);
	}

	public static void updateDatabase(JTable table) {
		if(table.getRowCount() == 0)
			return;
		Object[] values = new Object[NUM_COLUMNS];
		for(int i=0;i<table.getRowCount();i++) {
			for(int j=0;j<NUM_COLUMNS;j++) {
				System.out.println(i +","+ j);
				values[j] = table.getValueAt(i, j);
			}
			Student student = new Student(Integer.parseInt((String)values[ID_STUDENT]),
										 (String)values[FIRST_NAME],
										 (String)values[LAST_NAME],
										 Actions.toSqlDateFormat(((String)values[DATE_OF_BIRTH])),
										 (String)values[GENDER],
										 (String)values[EMAIL],
										 (String)values[PROFESSION],
										 Integer.parseInt((String)values[CREDITS]),
										 Integer.parseInt((String)values[GRADUATE_YEAR]));
			Main.db.updateStudent(student);
		}
		
		System.out.println("Actions>> The Database has been updated.");
	}
}
