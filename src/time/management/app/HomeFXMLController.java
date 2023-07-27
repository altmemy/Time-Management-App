/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package time.management.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 *
 * @author altmemy
 * my phone number +905363094803
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
    @FXML
    private Label errorMasg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logInMethod(ActionEvent event) {
        if (checkForData()){
            try {
                PrintWriter output = new PrintWriter("currentUser.txt");
                output.println(userName.getText());
                output.close();
                goToWindow("MainPageFXML.fxml",loginBt,"Main Page");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    private void greateAccount(MouseEvent event) {
        try {
            goToWindow("GreateAccountFXML.fxml",newAccount,"Create Account");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
//    private void goToWindow(String windowName,Label button,String title) throws IOException {
//        Stage stage = (Stage) button.getScene().getWindow();
//        stage.close();
//        Parent root = FXMLLoader.load(getClass().getResource(windowName));
//        Scene scene = new Scene(root);
//        stage.setTitle(title);
//        stage.setScene(scene);
//        stage.show();
//    }
//    private void goToWindow(String windowName,Button button,String title) throws IOException {
//        Stage stage = (Stage) button.getScene().getWindow();
//        stage.close();
//        Parent root = FXMLLoader.load(getClass().getResource(windowName));
//        Scene scene = new Scene(root);
//        stage.setTitle(title);
//        stage.setScene(scene);
//        stage.show();
//    }
private void goToWindow(String windowName, Node node, String title) throws IOException {
    Stage stage = (Stage) node.getScene().getWindow();
    stage.close();
    Parent root = FXMLLoader.load(getClass().getResource(windowName));
    Scene scene = new Scene(root);
    stage.setTitle(title);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
}

    private boolean checkForData(){
        if(userName.getText().isEmpty() || password.getText().isEmpty()){
            errorMasg.setText("Please enter your username and password");
            return false;
        }
        File file = new File(userName.getText()+".txt");
        if(!file.exists()){
            errorMasg.setText("This username is not exist");
            return false;
        }
        try {
            Scanner input = new Scanner(file);
            String[] data = input.nextLine().split(",");
            String pass = data[1];
            if(!pass.equals(password.getText())){
                errorMasg.setText("The password is wrong");
                return false;
            }
        } catch (FileNotFoundException e) {
            errorMasg.setText("Something went wrong");
            return false;
        }
        return true;
    }
}
