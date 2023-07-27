package time.management.app;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Task {
    private String title;
    private String StartTime;
    private String EndTime;
    private String description;
    private String priority;
    private String status;

    public Task(String title, String StartTime, String EndTime, String description, String priority) {
        this.title = title;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void saveTask() {
        File file = new File("currentUser.txt");
        try {
            Scanner input = new Scanner(file);
            String userName = input.nextLine();
            input.close();
            File file2 = new File(userName + ".txt");
            FileWriter output = new FileWriter(file2, true);
            output.write(this.toString());
            output.close();

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public String toString() {
        return title+","+StartTime+","+EndTime+","+description+","+priority+","+status+"\n";
    }
}
