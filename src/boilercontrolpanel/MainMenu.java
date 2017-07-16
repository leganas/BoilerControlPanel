package boilercontrolpanel;/**
 * Created by AndreyLS on 22.03.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        primaryStage.setTitle("Консоль управления котельной");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }
}
