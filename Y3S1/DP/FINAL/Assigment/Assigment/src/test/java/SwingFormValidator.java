import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SwingFormValidator extends JFrame {
    private JTextField nameInput;
    private JTextField addressInput;
    private JLabel feedbackLabel;

    public SwingFormValidator() {
        setTitle("Form Validation Example");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        JLabel nameLabel = new JLabel("Client Name:");
        nameInput = new JTextField(20);
        JLabel addressLabel = new JLabel("Client Address:");
        addressInput = new JTextField(20);
        JButton submitButton = new JButton("Add Client");
        feedbackLabel = new JLabel("", SwingConstants.CENTER);

        // Set up feedback label properties
        feedbackLabel.setForeground(Color.RED);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Layout setup
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(nameInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(addressLabel, gbc);

        gbc.gridx = 1;
        panel.add(addressInput, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(submitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(feedbackLabel, gbc);

        add(panel);

        // Button action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
    }

    private void handleSubmit() {
        // Clear previous feedback
        feedbackLabel.setText("");

        // Validate form input
        FormValidator validator = new FormValidator();
        validator.validatePresence("Client Name", nameInput.getText());
        validator.validatePresence("Client Address", addressInput.getText());

        if (validator.hasErrors()) {
            feedbackLabel.setText("<html>Errors: " + String.join("<br>", validator.getErrorMessages()) + "</html>");
        } else {
            feedbackLabel.setForeground(Color.GREEN);
            feedbackLabel.setText("Client added successfully!");
            nameInput.setText("");
            addressInput.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingFormValidator form = new SwingFormValidator();
            form.setVisible(true);
        });
    }
}

class FormValidator {
    private final List<String> errorMessages = new ArrayList<>();

    public void validatePresence(String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            errorMessages.add(fieldName + " is required.");
        }
    }

    public boolean hasErrors() {
        return !errorMessages.isEmpty();
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }

    public void clearErrors() {
        errorMessages.clear();
    }
}
