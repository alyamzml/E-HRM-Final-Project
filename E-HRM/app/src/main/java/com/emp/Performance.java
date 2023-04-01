package com.emp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Performance {
    Attendance at = new Attendance();
    OverTime ot = new OverTime();

    private String status;
    private int numAttend = 0;
    private int rows=0;
    private Integer[] attend;

    // This method calculates the performance status based on attendance and overtime
    public String getStatus(){
        // Get the attendance status array from the Attendance object

        attend = at.getArrayStatus();

        // Count the number of "1" values in the attendance status array
        int numAttend = 0;
        for (int atts : attend){
            if (atts == 1){
                numAttend++;
            }
        }
        // Get the total overtime from the OverTime object
        double totalOT = ot.getTotalTime();

        // Determine the performance status based on attendance and overtime
        if (numAttend >= (0.75 * rows)){
            if (totalOT > 80){
                status = "Excellent";
            }
            else {
                status = "Good";
            }
        }
        else if (numAttend >= (0.5 * rows)) {
            if (totalOT > 80){
                status = "Good";
            }
            else {
                status = "Unsatisfactory";
            }
        }
        else{
            status = "Unsatisfactory";
        }

        // Return the performance status
        return status;
    }
}