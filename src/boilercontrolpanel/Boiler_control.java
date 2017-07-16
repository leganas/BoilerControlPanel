package boilercontrolpanel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.CookieHandler;

public class Boiler_control extends Application {

    private static String ABS_PATH_TO_JFXWEBKIT_DLL = "";

    public enum Status {
        login_request,
        control_request,
        finish
    }

    Status status = Status.login_request;

    String setLogin = "document.getElementsByName('username')[0].value='" + PostLP.login + "';";
    String setPassword = "document.getElementsByName('password')[0].value='" + PostLP.password + "';";
    String btnLogin = "document.getElementById('btnLogin').click();";



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("boiler_control.fxml"));

        if ("Windows XP".equals(System.getProperty("os.name"))){
            ABS_PATH_TO_JFXWEBKIT_DLL = System.getProperty("user.dir");
            System.load(ABS_PATH_TO_JFXWEBKIT_DLL + "\\jfxwebkit.dll");
        }

        WebView webView = (WebView) root.lookup("#web_view");
        webView.setVisible(false);
        java.net.CookieHandler.setDefault(new java.net.CookieManager());

        WebEngine webEngine = webView.getEngine();

//        Button button = (Button) root.lookup("#btn");
//
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                login(webEngine);
//            }
//        });


        webEngine.load("https://www.econet24.com");

        Label label = (Label) root.lookup("#label");
        Label label2 = (Label) root.lookup("#lbl2");

        ProgressBar progressBar = (ProgressBar) root.lookup("#prbar");

        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == State.SUCCEEDED) {
                            // Если страница загрузилась то
                            if (status == Status.control_request) {
                                progressBar.setVisible(false);
                                label.setVisible(false);
                                label2.setVisible(false);
                                webView.setVisible(true);
                                status = Status.finish;
                            } else

                            if (status == Status.login_request) {
                                // Авторизуемся
                                login(webEngine);
                                status = Status.control_request;
                                Thread th = new Thread(new Runnable() {
                                    int tik=0;
                                    @Override
                                    public void run() {
                                        do {
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            tik++;
                                            if (tik == 7 && progressBar.getProgress() == 0) {
                                                login(webEngine);
                                                tik = 0;
                                            }
                                            if (progressBar.getProgress() > 0.5f) {
                                                progressBar.setVisible(false);
                                                label.setVisible(false);
                                                label2.setVisible(false);
                                                webView.setVisible(true);
                                            }
                                            System.out.println(""+tik);
                                            System.out.println(""+progressBar.getProgress());
                                        } while (status != Status.finish);
                                    }
                                });
                                th.start();
                                label.setText("Загрузка интерфейса управления горелкой");
                            }

                        }
                    }
                });

        primaryStage.setTitle("Консоль управления котельной");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    private void login(WebEngine webEngine) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.executeScript(setLogin);
                webEngine.executeScript(setPassword);
                webEngine.executeScript(btnLogin);
            }
        });
    }


    public static void main(String[] args) {
        if (args.length != 0){
            PostLP.login = args[0];
            PostLP.password = args[1];
        }
        launch(args);
    }
}
