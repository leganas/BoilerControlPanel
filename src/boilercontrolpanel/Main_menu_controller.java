package boilercontrolpanel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by AndreyLS on 22.03.2017.
 */
public class Main_menu_controller {

    @FXML
    Button bt1;

    @FXML
    Button bt2;


    public void bt1_click(ActionEvent actionEvent) throws Exception {
        PostLP.login = "VPKBania@gmail.com";
        PostLP.password = "8Pv5x2bZ8P";
        Application ap = new Boiler_control();
        ap.start(new Stage());
   }


    public void bt2_click(ActionEvent actionEvent) throws Exception {
        PostLP.login = "bania42017@gmail.com";
        PostLP.password = "B0lshak0V_";
        Application ap = new Boiler_control();
        ap.start(new Stage());
    }
}
