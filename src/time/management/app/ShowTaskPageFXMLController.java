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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author musht
 */
public class ShowTaskPageFXMLController implements Initializable {

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
    private Button back;
    @FXML
    private Button delete;
    @FXML
    private Button complite;
    @FXML
    private TextArea desc;
    private Task selectedItem;


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
                tasks.add(task);
            }

            // Set the list as the items for the table
            table.setItems(tasks);
            userFileScanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    private void handleRowSelect(MouseEvent event) {
        selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            desc.setText(selectedItem.getDescription());
        }
        delete.setDisable(false);
        complite.setDisable(false);

    }

    @FXML
    private void onBack(ActionEvent event) {
        try {
            goToWindow("MainPageFXML.fxml", back, "Main Page");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void onDelete(ActionEvent event) {
//        i need delete selected row and rewrite new db to file after update
        File file = new File("currentUser.txt");
        try {
            Scanner input = new Scanner(file);
            String userName = input.nextLine();
            input.close();
            File userFile = new File(userName + ".txt");
            Scanner userFileScanner = new Scanner(userFile);
            String userFileData = userFileScanner.nextLine();
            userFileScanner.close();
            PrintWriter writer = new PrintWriter(userFile);
            writer.println(userFileData);
//            get selected row and delete it
            table.getItems().remove(selectedItem);
            for (Task task : table.getItems()) {
                writer.println(task.getTitle() + "," + task.getStartTime() + "," + task.getEndTime() + "," + task.getDescription() + "," + task.getPriority() + "," + task.getStatus());
            }
            writer.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //
//    @FXML
//    private void onComplite(ActionEvent event) {
//        //change selected row status to complited and rewrite new db to file after update
//        File file = new File("currentUser.txt");
//        try {
//            Scanner input = new Scanner(file);
//            String userName = input.nextLine();
//            input.close();
//            File userFile = new File(userName + ".txt");
//            Scanner userFileScanner = new Scanner(userFile);
//            String userFileData = userFileScanner.nextLine();
//            userFileScanner.close();
//            userFile.delete();
//            PrintWriter writer = new PrintWriter(userName + ".txt");
//            writer.println(userFileData);
//            table.getItems().get(table.getSelectionModel().getSelectedIndex()).setStatus("Complited");
//            for (Task task : table.getItems()) {
//                writer.println(task.getTitle() + "," + task.getStartTime() + "," + task.getEndTime() + "," + task.getDescription() + "," + task.getPriority() + "," + task.getStatus());
//            }
//            writer.close();
//            getDetails();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    @FXML
    private void onComplite(ActionEvent event) {
        // Change selected row status to completed and rewrite new db to file after update
        File file = new File("currentUser.txt");
        try {
            Scanner input = new Scanner(file);
            String userName = input.nextLine();
            input.close();
            Task taskToBeCompleted = table.getSelectionModel().getSelectedItem();
            if (taskToBeCompleted != null) {
                taskToBeCompleted.setStatus("Completed");
                File userFile = new File(userName + ".txt");
                Scanner userFileScanner = new Scanner(userFile);
                String userFileData = userFileScanner.nextLine();
                userFileScanner.close();
                PrintWriter writer = new PrintWriter(userFile);
                writer.println(userFileData);
                for (Task task : table.getItems()) {
                    writer.println(task.getTitle() + "," + task.getStartTime() + "," + task.getEndTime() + "," + task.getDescription() + "," + task.getPriority() + "," + task.getStatus());
                }
                writer.close();
                getDetails();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
}
