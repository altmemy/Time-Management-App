/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package time.management.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author musht
 */
public class AddTaskPageFXMLController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private RadioButton low;
    @FXML
    private ToggleGroup priority;
    @FXML
    private RadioButton medium;
    @FXML
    private RadioButton high;
    @FXML
    private TextArea description;
    @FXML
    private Button backBt;
    @FXML
    private Button saveBt;
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
    private void backMethod(ActionEvent event) {
        try {
            goToWindow("MainPageFXML.fxml",backBt,"Main Page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void saveMethod(ActionEvent event) {
        try {
            saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean checkForData(){
        if (title.getText().isEmpty() || startDate.getValue() == null || endDate.getValue() == null || description.getText().isEmpty()){
            errorMasg.setText("Please fill all the fields");
            return false;
        }
        if (startDate.getValue().isAfter(endDate.getValue())){
            errorMasg.setText("Start date can't be after end date");
            return false;
        }

        return true;
    }

    private void goToWindow(String windowName, Button button, String title) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource(windowName));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    private void saveData() throws IOException{
        if (checkForData()){
            String priority = "";
            if (low.isSelected()){
                priority = "low";
            }else if (medium.isSelected()){
                priority = "medium";
            }else if (high.isSelected()){
                priority = "high";
            }
            //    public Task(String title, String StartTime, String EndTime, String description, String priority) {
            Task task = new Task(title.getText(),startDate.getValue().toString(),endDate.getValue().toString(),description.getText(),priority);
            task.saveTask();
            goToWindow("MainPageFXML.fxml",saveBt,"Main Page");
        }
    }
}
