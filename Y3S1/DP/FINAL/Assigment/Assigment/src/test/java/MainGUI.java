import Feedback.FormValidator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bank Management System");

        // Create UI components
        Label nameLabel = new Label("Client Name:");
        TextField nameInput = new TextField();
        Label addressLabel = new Label("Client Address:");
        TextField addressInput = new TextField();
        Button submitButton = new Button("Add Client");

        Label feedbackLabel = new Label();

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameInput, 1, 0);
        gridPane.add(addressLabel, 0, 1);
        gridPane.add(addressInput, 1, 1);
        gridPane.add(submitButton, 1, 2);
        gridPane.add(feedbackLabel, 1, 3);

        // Button action
        submitButton.setOnAction(e -> {
            String name = nameInput.getText();
            String address = addressInput.getText();

            FormValidator validator = new FormValidator();
            validator.validatePresence("Client Name", name);
            validator.validatePresence("Client Address", address);

            if (validator.hasErrors()) {
                feedbackLabel.setText("Errors: " + String.join(", ", validator.getErrorMessages()));
            } else {
                feedbackLabel.setText("Client added successfully!");
                nameInput.clear();
                addressInput.clear();
            }
        });


        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
