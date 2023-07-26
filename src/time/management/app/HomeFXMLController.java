/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package time.management.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author musht
 */
public class HomeFXMLController implements Initializable {
    
    private Label label;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBt;
    @FXML
    private Label newAccount;
    
    private void handleButtonAction(ActionEvent event) {
        
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logInMethod(ActionEvent event) {
    }

    @FXML
    private void greateAccount(MouseEvent event) {
        System.out.println("You clicked me!");
    }
    
}
