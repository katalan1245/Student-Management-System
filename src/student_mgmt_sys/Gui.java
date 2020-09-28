package student_mgmt_sys;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Gui {

	private JFrame frame;
	private JTable table;
	private JTextPane summaryTextPane;
	private JTextField sqlTextField;
	private JTextField emailField;
	private JTextField firstNameField;
	private JTextField professionField;
	private JTextField lastNameField;
	private JTextField creditsField;
	private JTextField dateOfBirthField;
	private JTextField graduateYearField;
	private JTextField genderField;
	private boolean formMode = true;

	public Gui() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Student Management System");
		frame.getContentPane().setBackground(new Color(0, 120, 215));
		frame.setBounds(250, 150, 1310, 810);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		createTitle();	
		createButtons();
		createTable();
		createSummary();
		createInput();
		
		frame.setVisible(true);
	}
	
	private void createTitle() {
		frame.getContentPane().setLayout(null);
		JLabel title = new JLabel("Student Management System");
		title.setBounds(425, 0, 465, 44);
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Exo Medium", Font.BOLD, 32));
		frame.getContentPane().add(title);
	}
	
	private void createButtons() {
		
		JButton createButton = new JButton("Create Student");
		createButton.setBounds(10, 710, 160, 50);
		createButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GUI>> Create New Student:");
				if(formMode) {
					Student studentToAdd = selectData();
					if(studentToAdd == null) {
						JOptionPane.showMessageDialog(null, "Didn't Create Student","Make sure to fill all the data correctly!",JOptionPane.ERROR_MESSAGE);
						System.out.println("GUI>> Student didn't create.");
					}
					else
						Actions.createStudent(studentToAdd);
				} else {
					Actions.sendCommand(sqlTextField.getText());
				}
				reloadTable();
				
			}
		});
		createButton.setFont(new Font("Arial", Font.BOLD, 16));
		createButton.setForeground(Color.BLACK);
		frame.getContentPane().add(createButton);
		
		JButton updateButton = new JButton("Update Database");
		updateButton.setBounds(178, 710, 165, 50);
		updateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GUI>> Update Database:");
				Actions.updateDatabase(table);
				reloadTable();
			}
		});
		updateButton.setForeground(Color.BLACK);
		updateButton.setFont(new Font("Arial", Font.BOLD, 16));
		frame.getContentPane().add(updateButton);
		
		JButton deleteButton = new JButton("Delete Student");
		deleteButton.setBounds(350, 710, 160, 50);
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GUI>> Delete Student:");
			}
		});
		deleteButton.setForeground(Color.BLACK);
		deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
		frame.getContentPane().add(deleteButton);
		
		JButton copySummaryButton = new JButton("Copy Summary");
		copySummaryButton.setBounds(520, 710, 160, 50);
		copySummaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		copySummaryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GUI>> Copy Summary To Clipboard:");
				Actions.copySummaryToClipboard(summaryTextPane);
			}
		});
		copySummaryButton.setForeground(Color.BLACK);
		copySummaryButton.setFont(new Font("Arial", Font.BOLD, 16));
		frame.getContentPane().add(copySummaryButton);
		
		JButton saveTableButton = new JButton("Save Database");
		saveTableButton.setBounds(695, 710, 160, 50);
		saveTableButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String ans = JOptionPane.showInputDialog("Enter filename (Include extension): ");
				if(ans == null || ans.equals("")) {
					JOptionPane.showMessageDialog(null, "Enter filename!");
					return;
				}
				System.out.println("GUI>> Write Table To File " + ans + ":");
				Actions.writeTableToExcel(table,ans);
			}
		});
		saveTableButton.setForeground(Color.BLACK);
		saveTableButton.setFont(new Font("Arial", Font.BOLD, 16));
		frame.getContentPane().add(saveTableButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(1205, 710, 79, 50);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GUI>> Exiting...");
				System.exit(0);
			}
		});
		exitButton.setForeground(Color.BLACK);
		exitButton.setFont(new Font("Arial", Font.BOLD, 24));
		frame.getContentPane().add(exitButton);
		
		JButton clearSummaryButton = new JButton("Clear Summary");
		clearSummaryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GUI>> Clearing Summary:");
				Actions.clearSummary(summaryTextPane);
			}
		});
		clearSummaryButton.setForeground(Color.BLACK);
		clearSummaryButton.setFont(new Font("Arial", Font.BOLD, 16));
		clearSummaryButton.setBounds(865, 710, 160, 50);
		frame.getContentPane().add(clearSummaryButton);
		
		JButton clearTableButton = new JButton("Clear Database");
		clearTableButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int dr = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the database?\nYou cannot restore the data!");
				if(dr == JOptionPane.NO_OPTION || dr == JOptionPane.CANCEL_OPTION)
					return;
				System.out.println("GUI>> Clearing Table:");
				Actions.clearDatabase();
				reloadTable();
			}
		});
		clearTableButton.setForeground(Color.BLACK);
		clearTableButton.setFont(new Font("Arial", Font.BOLD, 16));
		clearTableButton.setBounds(1035, 710, 160, 50);
		frame.getContentPane().add(clearTableButton);
	}
	
	private void createTable() {
		JScrollPane tableScroll = new JScrollPane();
		tableScroll.setBounds(10, 506, 1274, 193);
		frame.getContentPane().add(tableScroll);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Student ID", "First Name", "Last Name", "Date Of Birth", "Gender", "Email", "Profession", "Credits", "Graduate Year"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(8).setPreferredWidth(82);
		tableScroll.setViewportView(table);
		fillTable();
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) { }
			
			@Override
			public void mouseExited(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GUI>> Selected Row:");
				summaryTextPane.setText(Actions.writeSummary(table, table.getSelectedRow()));
			}
		});
	}
	
	private void createSummary() {
		JScrollPane summaryScroll = new JScrollPane();
		summaryScroll.setBounds(837, 51, 447, 444);
		frame.getContentPane().add(summaryScroll);
		
		summaryTextPane = new JTextPane();
		summaryTextPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Actions.copySummaryToClipboard(summaryTextPane);
				System.out.println("GUI>> Summary Copied To Clipboard");
			}});
		summaryScroll.setViewportView(summaryTextPane);
		summaryTextPane.setText("Select Student From The Table:");
		summaryTextPane.setFont(new Font("Candara", Font.BOLD, 24));
		summaryTextPane.setEditable(false);
	}
	
	private void createInput() {
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(10, 51, 813, 452);
		inputPanel.setBackground(Color.decode("#7bcae4"));
		frame.getContentPane().add(inputPanel);
		inputPanel.setLayout(null);
		
		createSqlCommandPanel(inputPanel);
		
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(null);
		emailPanel.setBackground(new Color(127, 166, 214));
		emailPanel.setBounds(443, 10, 360, 40);
		inputPanel.add(emailPanel);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(new Font("Arial", Font.BOLD, 20));
		emailLabel.setBounds(4, 7, 120, 26);
		emailPanel.add(emailLabel);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		emailField.setColumns(10);
		emailField.setBounds(140, 4, 210, 32);
		emailPanel.add(emailField);
		
		JPanel firstNamePanel = new JPanel();
		firstNamePanel.setLayout(null);
		firstNamePanel.setBackground(new Color(127, 166, 214));
		firstNamePanel.setBounds(10, 10, 360, 40);
		inputPanel.add(firstNamePanel);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		firstNameLabel.setBounds(4, 7, 120, 26);
		firstNamePanel.add(firstNameLabel);
		
		firstNameField = new JTextField();
		firstNameField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		firstNameField.setColumns(10);
		firstNameField.setBounds(140, 4, 210, 32);
		firstNamePanel.add(firstNameField);
		
		JPanel professionPanel = new JPanel();
		professionPanel.setLayout(null);
		professionPanel.setBackground(new Color(127, 166, 214));
		professionPanel.setBounds(443, 61, 360, 40);
		inputPanel.add(professionPanel);
		
		JLabel professionLabel = new JLabel("Profession:");
		professionLabel.setFont(new Font("Arial", Font.BOLD, 20));
		professionLabel.setBounds(4, 7, 120, 26);
		professionPanel.add(professionLabel);
		
		professionField = new JTextField();
		professionField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		professionField.setColumns(10);
		professionField.setBounds(140, 4, 210, 32);
		professionPanel.add(professionField);
		
		JPanel lastNamePanel = new JPanel();
		lastNamePanel.setLayout(null);
		lastNamePanel.setBackground(new Color(127, 166, 214));
		lastNamePanel.setBounds(10, 61, 360, 40);
		inputPanel.add(lastNamePanel);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lastNameLabel.setBounds(4, 7, 120, 26);
		lastNamePanel.add(lastNameLabel);
		
		lastNameField = new JTextField();
		lastNameField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		lastNameField.setColumns(10);
		lastNameField.setBounds(140, 4, 210, 32);
		lastNamePanel.add(lastNameField);
		
		JPanel creditsPanel = new JPanel();
		creditsPanel.setLayout(null);
		creditsPanel.setBackground(new Color(127, 166, 214));
		creditsPanel.setBounds(443, 112, 360, 40);
		inputPanel.add(creditsPanel);
		
		JLabel creditsLabel = new JLabel("Credits:");
		creditsLabel.setFont(new Font("Arial", Font.BOLD, 20));
		creditsLabel.setBounds(4, 7, 120, 26);
		creditsPanel.add(creditsLabel);
		
		creditsField = new JTextField();
		creditsField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		creditsField.setColumns(10);
		creditsField.setBounds(140, 4, 210, 32);
		creditsPanel.add(creditsField);
		
		JPanel dateOfBirthPanel = new JPanel();
		dateOfBirthPanel.setLayout(null);
		dateOfBirthPanel.setBackground(new Color(127, 166, 214));
		dateOfBirthPanel.setBounds(10, 112, 360, 40);
		inputPanel.add(dateOfBirthPanel);
		
		JLabel dateOfBirthLabel = new JLabel("Date Of Birth:");
		dateOfBirthLabel.setFont(new Font("Arial", Font.BOLD, 20));
		dateOfBirthLabel.setBounds(4, 7, 131, 26);
		dateOfBirthPanel.add(dateOfBirthLabel);
		
		dateOfBirthField = new JTextField();
		dateOfBirthField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		dateOfBirthField.setColumns(10);
		dateOfBirthField.setBounds(140, 4, 210, 32);
		dateOfBirthPanel.add(dateOfBirthField);
		
		JPanel graduateYearPanel = new JPanel();
		graduateYearPanel.setLayout(null);
		graduateYearPanel.setBackground(new Color(127, 166, 214));
		graduateYearPanel.setBounds(443, 163, 360, 40);
		inputPanel.add(graduateYearPanel);
		
		JLabel graduateYearLabel = new JLabel("Graduate Year:");
		graduateYearLabel.setFont(new Font("Arial", Font.BOLD, 18));
		graduateYearLabel.setBounds(4, 7, 133, 26);
		graduateYearPanel.add(graduateYearLabel);
		
		graduateYearField = new JTextField();
		graduateYearField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		graduateYearField.setColumns(10);
		graduateYearField.setBounds(140, 4, 210, 32);
		graduateYearPanel.add(graduateYearField);
		
		JPanel genderPanel = new JPanel();
		genderPanel.setLayout(null);
		genderPanel.setBackground(new Color(127, 166, 214));
		genderPanel.setBounds(10, 163, 360, 40);
		inputPanel.add(genderPanel);
		
		JLabel genderLabel = new JLabel("Gender:");
		genderLabel.setFont(new Font("Arial", Font.BOLD, 20));
		genderLabel.setBounds(4, 7, 120, 26);
		genderPanel.add(genderLabel);
		
		genderField = new JTextField();
		genderField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		genderField.setColumns(10);
		genderField.setBounds(140, 4, 210, 32);
		genderPanel.add(genderField);
	}
	
	private void createSqlCommandPanel(JPanel inputPanel) {
		JPanel sqlPanel = new JPanel();
		sqlPanel.setBackground(Color.decode("#2b84ba"));
		sqlPanel.setBounds(10, 350, 793, 91);
		inputPanel.add(sqlPanel);
		sqlPanel.setLayout(null);
		
		sqlTextField = new JTextField();
		sqlTextField.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
		sqlTextField.setBounds(10, 45, 773, 38);
		sqlPanel.add(sqlTextField);
		sqlTextField.setColumns(10);
		sqlTextField.setEnabled(false);
		
		JRadioButton sqlRadioButton = new JRadioButton("Enter MySQL Command");
		sqlRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchMode();
			}
		});
		sqlRadioButton.setForeground(Color.WHITE);
		sqlRadioButton.setBackground(Color.decode("#2b84ba"));
		sqlRadioButton.setBounds(6, 0, 303, 38);
		sqlPanel.add(sqlRadioButton);
		sqlRadioButton.setFont(new Font("Segoe UI Black", Font.PLAIN, 24));
	}

	private void fillTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		LinkedHashMap<Integer, LinkedHashMap<String, String>> data = Main.db.getTable();
		for(int i=1;i<Main.db.getRowCount();i++) {
			model.addRow(new Object[] {data.get(i).get("studentId"),
									   data.get(i).get("firstName"),
									   data.get(i).get("lastName"),
									   Actions.dateFormat(data.get(i).get("dateOfBirth")),
									   data.get(i).get("gender"),
									   data.get(i).get("email"),
									   data.get(i).get("profession"),
									   data.get(i).get("credits"),
									   data.get(i).get("graduateYear")});
		}
	}

	private void deleteAllRows() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		for(int i=rowCount-1;i>=0;i--) {
			dm.removeRow(i);
		}
	}

	private Student selectData() {
		if(firstNameField.getText().equals("") ||
		   lastNameField.getText().equals("") ||
		   dateOfBirthField.getText().equals("") ||
		   genderField.getText().equals("") ||
		   emailField.getText().equals("") ||
		   professionField.getText().equals("") ||
		   creditsField.getText().equals("") ||
		   graduateYearField.getText().equals(""))
			return null;
		
		return new StudentBuilder().
				firstName(firstNameField.getText()).
				lastName(lastNameField.getText()).
				dateOfBirth(Actions.toSqlDateFormat(dateOfBirthField.getText())).
				gender(genderField.getText()).
				email(emailField.getText()).
				profession(professionField.getText()).
				credits(Integer.parseInt(creditsField.getText())).
				graduateYear(Integer.parseInt(graduateYearField.getText()))
				.buildStudent();
		
	}
	
	private void reloadTable() {
		deleteAllRows();
		fillTable();
	}

	private void switchMode() {
		firstNameField.setEnabled(!this.formMode);
		lastNameField.setEnabled(!this.formMode);
		dateOfBirthField.setEnabled(!this.formMode);
		genderField.setEnabled(!this.formMode);
		emailField.setEnabled(!this.formMode);
		professionField.setEnabled(!this.formMode);
		creditsField.setEnabled(!this.formMode);
		graduateYearField.setEnabled(!this.formMode);
		sqlTextField.setEnabled(this.formMode);
		this.formMode = !this.formMode;
	}
	/*
	private void enableFormMode() {
		sqlTextField.setEnabled(false);
		this.formMode = true;
	}*/
}