/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package time.management.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author musht
 */
public class CreateAccountFXMLController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField rePassword;
    @FXML
    private Button backBt;
    @FXML
    private Button signUpBt;
    @FXML
    private Label errorMasg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BackMeethod(ActionEvent event) {
        try {
            goToWindow("HomeFXML.fxml",backBt,"Login Page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToWindow(String windowName,Button button,String title) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource(windowName));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void signUpMethod(ActionEvent event) {
        if(checkForData()){
            try {
                createAccount();
                goToWindow("HomeFXML.fxml",signUpBt,"Home Page");
            } catch (FileNotFoundException ex) {
                System.out.println("Error in create account");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean checkForData(){
        if(name.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty() || rePassword.getText().isEmpty()){
            errorMasg.setText("Please fill all the fields");
            return false;
        }
        //password contains 8 characters at least
        if(password.getText().length() < 8){
            errorMasg.setText("Password must be at least 8 characters");
            return false;
        }
        if(!password.getText().equals(rePassword.getText())){
            errorMasg.setText("Password and re-password are not the same");
            return false;
        }
        //check if username is number and letters only
        for(int i = 0; i < username.getText().length(); i++){
            if(!Character.isLetterOrDigit(username.getText().charAt(i))){
                errorMasg.setText("Username must be letters and numbers only");
                return false;
            }
        }
        //check if username is already exist
        File file = new File( username.getText() + ".txt");
        if(file.exists()){
            errorMasg.setText("Username is already exist");
            return false;
        }

        return true;
    }
    private void createAccount() throws FileNotFoundException {
        //create file
        PrintWriter writer = new PrintWriter(username.getText() + ".txt");
        writer.println(username.getText()+","+password.getText()+","+name.getText());
        writer.close();

    }
}
