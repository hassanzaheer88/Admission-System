import javax.swing.*;
import java.awt.*;

class BSCSAdmissionGUI {
    static int maxStudents = 1000;
    static int studentCount = 0;
    static int formNumberCounter = 1;
    static int rollNumberCounter = 1;

    static int[] formNumbers = new int[maxStudents];
    static int[] rollNumbers = new int[maxStudents];
    static String[] names = new String[maxStudents];
    static String[] fatherNames = new String[maxStudents];
    static double[] matricMarks = new double[maxStudents];
    static double[] fscMarks = new double[maxStudents];
    static double[] testMarks = new double[maxStudents];
    static double[] interviewMarks = new double[maxStudents];
    static double[] finalScores = new double[maxStudents];
    static boolean[] eligible = new boolean[maxStudents];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createMainMenu());
    }
    public static void createMainMenu() {
        JFrame frame = new JFrame("BS(CS) Admission Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));
        
        JButton formEntryButton = new JButton("Form Entry");
        JButton testEntryButton = new JButton("Test & Interview Marks Entry");
        JButton eligibleListButton = new JButton("Display Test List");
        JButton meritListButton = new JButton("Display Final Merit List");
        JButton exitButton = new JButton("Exit");

        formEntryButton.addActionListener(e -> openFormEntry(frame));
        testEntryButton.addActionListener(e -> openTestEntry(frame));
        eligibleListButton.addActionListener(e -> displayEligibleStudents(frame));
        meritListButton.addActionListener(e -> displayMeritList(frame));
        exitButton.addActionListener(e -> frame.dispose());

        frame.add(formEntryButton);
        frame.add(testEntryButton);
        frame.add(eligibleListButton);
        frame.add(meritListButton);
        frame.add(exitButton);

        frame.setVisible(true);
    }
    public static void openFormEntry(JFrame parentFrame) {
        JFrame frame = new JFrame("Form Entry");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(7, 2));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel fatherNameLabel = new JLabel("Father's Name:");
        JTextField fatherNameField = new JTextField();
        JLabel matricLabel = new JLabel("Matric Marks (out of 1100):");
        JTextField matricField = new JTextField();
        JLabel fscLabel = new JLabel("F.Sc Marks (out of 1100):");
        JTextField fscField = new JTextField();
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");
        submitButton.addActionListener(e -> {
            if (studentCount >= maxStudents) {
                JOptionPane.showMessageDialog(frame, "Maximum number of students reached.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String name = nameField.getText();
                String fatherName = fatherNameField.getText();
                double matric = Double.parseDouble(matricField.getText());
                double fsc = Double.parseDouble(fscField.getText());
                if (matric > 1100) {
                    JOptionPane.showMessageDialog(frame, "Matric marks cannot exceed 1100.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (fsc > 1100) {
                    JOptionPane.showMessageDialog(frame, "F.Sc marks cannot exceed 1100.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                formNumbers[studentCount] = formNumberCounter++;
                names[studentCount] = name;
                fatherNames[studentCount] = fatherName;
                matricMarks[studentCount] = matric;
                fscMarks[studentCount] = fsc;
                testMarks[studentCount] = 0;
                interviewMarks[studentCount] = 0;
                if (fsc >= 550) {
                    eligible[studentCount] = true;
                    rollNumbers[studentCount] = rollNumberCounter++;
                    JOptionPane.showMessageDialog(frame, "Form submitted successfully. Roll Number: " + rollNumbers[studentCount] + " Form Number: " + formNumbers[studentCount]);
                } else {
                    eligible[studentCount] = false;
                    rollNumbers[studentCount] = 0;
                    JOptionPane.showMessageDialog(frame, "Student is not eligible.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                studentCount++;
                frame.dispose();
                parentFrame.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric values for marks.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            parentFrame.setVisible(true);
        });

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(fatherNameLabel);
        frame.add(fatherNameField);
        frame.add(matricLabel);
        frame.add(matricField);
        frame.add(fscLabel);
        frame.add(fscField);
        frame.add(submitButton);
        frame.add(backButton);

        parentFrame.setVisible(false);
        frame.setVisible(true);
    }

    public static void openTestEntry(JFrame parentFrame) {
        JFrame frame = new JFrame("Test & Interview Marks Entry");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        JLabel formNumberLabel = new JLabel("Form Number:");
        JTextField formNumberField = new JTextField();

        JLabel testMarksLabel = new JLabel("Test Marks (out of 50):");
        JTextField testMarksField = new JTextField();

        JLabel interviewMarksLabel = new JLabel("Interview Marks (out of 20):");
        JTextField interviewMarksField = new JTextField();

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(e -> {
            try {
                int formNumber = Integer.parseInt(formNumberField.getText());
                double test = Double.parseDouble(testMarksField.getText());
                double interview = Double.parseDouble(interviewMarksField.getText());

                if (test > 50) {
                    JOptionPane.showMessageDialog(frame, "Test marks cannot exceed 50.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (interview > 20) {
                    JOptionPane.showMessageDialog(frame, "Interview marks cannot exceed 20.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean found = false;

                for (int i = 0; i < studentCount; i++) {
                    if (formNumbers[i] == formNumber && eligible[i]) {
                        testMarks[i] = test;
                        interviewMarks[i] = interview;
                        JOptionPane.showMessageDialog(frame, "Marks updated successfully for Form Number: " + formNumber);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(frame, "Form number not found or student is not eligible.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                frame.dispose();
                parentFrame.setVisible(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric values for marks.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            parentFrame.setVisible(true);
        });

        frame.add(formNumberLabel);
        frame.add(formNumberField);
        frame.add(testMarksLabel);
        frame.add(testMarksField);
        frame.add(interviewMarksLabel);
        frame.add(interviewMarksField);
        frame.add(submitButton);
        frame.add(backButton);

        parentFrame.setVisible(false);
        frame.setVisible(true);
    }

    public static void displayEligibleStudents(JFrame parentFrame) {
        JFrame frame = new JFrame("Eligible Students List");
        frame.setSize(400, 300);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        for (int i = 0; i < studentCount; i++) {
            if (eligible[i]) {
                textArea.append("Form Number: " + formNumbers[i] + ", Roll Number: " + rollNumbers[i] + ", Name: " + names[i] + "\n");
            }
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            frame.dispose();
            parentFrame.setVisible(true);
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(backButton, BorderLayout.SOUTH);

        parentFrame.setVisible(false);
        frame.setVisible(true);
    }

    public static void displayMeritList(JFrame parentFrame) {
    JFrame frame = new JFrame("Final Merit List");
    frame.setSize(400, 400);
    frame.setLayout(new BorderLayout());

    JPanel inputPanel = new JPanel(new GridLayout(1, 2));
    JLabel numStudentsLabel = new JLabel("Number of Students:");
    JTextField numStudentsField = new JTextField();
    inputPanel.add(numStudentsLabel);
    inputPanel.add(numStudentsField);

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);

    JButton backButton = new JButton("Back");
    numStudentsField.addActionListener(e -> {
        textArea.setText(""); 
        int numStudents;
        try {
            numStudents = Integer.parseInt(numStudentsField.getText());
            if (numStudents <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < studentCount; i++) {
            if (eligible[i]) {
                finalScores[i] = (matricMarks[i] / 1100 * 0.1 * 100) +
                        (fscMarks[i] / 1100 * 0.6 * 100) +
                        (testMarks[i] / 50 * 0.3 * 100) +
                        (interviewMarks[i] / 20 * 0.1 * 100);
            }
        }

        // Sort students by final scores (descending)
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - i - 1; j++) {
                if (finalScores[j] < finalScores[j + 1]) {
                    int tempForm = formNumbers[j];
                    formNumbers[j] = formNumbers[j + 1];
                    formNumbers[j + 1] = tempForm;

                    int tempRoll = rollNumbers[j];
                    rollNumbers[j] = rollNumbers[j + 1];
                    rollNumbers[j + 1] = tempRoll;

                    String tempName = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempName;

                    String tempFatherName = fatherNames[j];
                    fatherNames[j] = fatherNames[j + 1];
                    fatherNames[j + 1] = tempFatherName;

                    double tempScore = finalScores[j];
                    finalScores[j] = finalScores[j + 1];
                    finalScores[j + 1] = tempScore;
                }
            }
        }

        // Display the top numStudents in the merit list
        int displayedCount = 0;
        for (int i = 0; i < studentCount && displayedCount < numStudents; i++) {
            if (eligible[i]) {
                textArea.append("Form Number: " + formNumbers[i] + ", Roll Number: " + rollNumbers[i] +
                        ", Name: " + names[i] + ", Final Score: " + finalScores[i] + "\n");
                displayedCount++;
            }
        }

        if (displayedCount == 0) {
            textArea.setText("No eligible students to display.");
        }
    });

    backButton.addActionListener(e -> {
        frame.dispose();
        parentFrame.setVisible(true);
    });

    frame.add(inputPanel, BorderLayout.NORTH);
    frame.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(backButton);

    frame.add(buttonPanel, BorderLayout.SOUTH);

    parentFrame.setVisible(false);
    frame.setVisible(true);
}
}