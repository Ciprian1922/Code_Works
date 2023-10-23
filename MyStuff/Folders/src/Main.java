import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;

public class Main {
    enum MonthNames {
        ENGLISH("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
        ROMANIAN("Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie");

        private String[] names;

        MonthNames(String... names) {
            this.names = names;
        }

        public String getName(int index) {
            if (index >= 0 && index < names.length) {
                return names[index];
            }
            return "";
        }
    }
    private JFrame frame;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField targetDirField;
    private JComboBox<String> languageComboBox;
    private JButton createButton;
    private JButton selectDirButton; // Button to open a directory chooser

    private MonthNames selectedMonthNames = MonthNames.ENGLISH; // Default to English

    public Main() {
        frame = new JFrame("Folder_Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(6, 2));

        frame.add(new JLabel("Start Date (e.g., 2021 09):"));
        startDateField = new JTextField();
        frame.add(startDateField);

        frame.add(new JLabel("End Date (e.g., 2023 10):"));
        endDateField = new JTextField();
        frame.add(endDateField);

        frame.add(new JLabel("Select Language:"));
        languageComboBox = new JComboBox<>();
        languageComboBox.addItem("English");
        languageComboBox.addItem("Romanian");
        languageComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = languageComboBox.getSelectedIndex();
                if (selectedIndex == 0) {
                    selectedMonthNames = MonthNames.ENGLISH;
                } else if (selectedIndex == 1) {
                    selectedMonthNames = MonthNames.ROMANIAN;
                }
            }
        });
        frame.add(languageComboBox);

        frame.add(new JLabel("Target Directory:"));
        targetDirField = new JTextField();
        frame.add(targetDirField);

        // Add a button to open a directory chooser
        selectDirButton = new JButton("Select Directory");
        selectDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dirChooser = new JFileChooser();
                dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Show only directories
                int option = dirChooser.showDialog(frame, "Select");
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedDir = dirChooser.getSelectedFile();
                    targetDirField.setText(selectedDir.getAbsolutePath());
                }
            }
        });
        frame.add(selectDirButton);

        createButton = new JButton("Create Folders");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFolders();
            }
        });
        frame.add(createButton);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void createFolders() {
        String startYearMonth = startDateField.getText();
        String endYearMonth = endDateField.getText();
        String targetDir = targetDirField.getText();

        try {
            Calendar startCalendar = Calendar.getInstance();
            String[] yearMonth = startYearMonth.split(" ");
            int startYear = Integer.parseInt(yearMonth[0]);
            int startMonth = Integer.parseInt(yearMonth[1]);

            Calendar endCalendar = Calendar.getInstance();
            yearMonth = endYearMonth.split(" ");
            int endYear = Integer.parseInt(yearMonth[0]);
            int endMonth = Integer.parseInt(yearMonth[1]);

            while (startYear < endYear || (startYear == endYear && startMonth <= endMonth)) {
                String folderName = startYear + " " + String.format("%02d", startMonth) + " " +
                        selectedMonthNames.getName(startMonth - 1);
                File folder = new File(targetDir, folderName);

                if (!folder.exists()) {
                    if (folder.mkdirs()) { // Create parent directories if they don't exist
                        System.out.println("Folder created successfully: " + folderName);
                    } else {
                        System.err.println("Failed to create the folder: " + folderName);
                    }
                } else {
                    System.err.println("Folder already exists: " + folderName);
                }

                if (startMonth == 12) {
                    startYear++;
                    startMonth = 1;
                } else {
                    startMonth++;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid date format.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
