import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class JavaFXTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello, JavaFX!");
        Scene scene = new Scene(label, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Test");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
