/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package time.management.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 *
 * @author altmemy
 * my phone number +905363094803
 */
public class MainPageFXMLController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem addTask;
    @FXML
    private MenuItem ShowTask;
    @FXML
    private TableView<Task> table;
    @FXML
    private TableColumn<Task, String> title;
    @FXML
    private TableColumn<Task, String> StartTime;
    @FXML
    private TableColumn<Task, String> EndTime;
    @FXML
    private TableColumn<Task, String> description;
    @FXML
    private TableColumn<Task, String> priority;
    @FXML
    private TableColumn<Task, String> status;
    @FXML
    private MenuItem out;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getDetails();
    }

    private void getDetails() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        StartTime.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        EndTime.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadTasksIntoTable();
    }

    private void loadTasksIntoTable() {
        File file = new File("currentUser.txt");
        try {
            Scanner input = new Scanner(file);
            String userName = input.nextLine();
            input.close();

            File userFile = new File(userName + ".txt");
            Scanner userFileScanner = new Scanner(userFile);
            userFileScanner.nextLine();

            // Create a list to hold the tasks
            ObservableList<Task> tasks = FXCollections.observableArrayList();

            // For each line in the file, create a task and add it to the list
            while (userFileScanner.hasNextLine()) {
                String line = userFileScanner.nextLine();
                String[] taskData = line.split(",");
                Task task = new Task(taskData[0], taskData[1], taskData[2], taskData[3], taskData[4]);
                task.setStatus(getStatus(taskData[1], taskData[2], taskData[5]));
                if (task.getPriority().equals("high"))
                    tasks.add(task);
            }

            // Set the list as the items for the table
            table.setItems(tasks);
            userFileScanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getStatus(String str, String end, String Status) {
        //check if the end time is before the current time this like end time is passed 2023-07-04
        if (end.compareTo(new Date().toString()) < 0) {
            if (!Status.equals("Completed")) {
                Status = "Passed";
            }

        } else if (str.compareTo(new Date().toString()) < 0) {
            if (Status.equals("null")) {
                Status = "In Progress";
            }
        } else {
            if (Status.equals("null"))
                Status = "Upcoming";
        }
        return Status;
    }

    @FXML
    private void addTaskMethod(ActionEvent event) {
        try {
            goToWindow("AddTaskPageFXML.fxml", addTask, "Add Task");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void ShowTaskMethod(ActionEvent event) {
        try {
            goToWindow("ShowTaskPageFXML.fxml", ShowTask, "Show Task");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void closeApp(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    private void aboutMethod(ActionEvent event) {
        System.out.println("about");
    }

    private void goToWindow(String windowName, MenuItem item, String title) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(windowName));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void logOut(ActionEvent event) {
        try {
            goToWindow("HomeFXML.fxml", out, "Login Page");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
