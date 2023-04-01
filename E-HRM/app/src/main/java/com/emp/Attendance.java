package com.emp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Attendance extends AppCompatActivity {
    Button btncheckIn, btncheckOut;
    EditText checkInDate, checkInTime, checkOutDate, checkOutTime;
    TextView checkInStatus;
    private String checkin_date, checkin_time, checkout_date, checkout_time;

    static ArrayList<Integer> attendanceList = new ArrayList<Integer>();

    // to return status, 1 if early than 0830 and 0 if not
    // the status is to be used in performance class (to evaluate the employee monthly performance)
    public static int getStatus(String time){
        int Time = Integer.parseInt(time);
        int status;
        if (Time <= 830){
            status = 1;
        }
        else status = 0;
        return status;
    }

    // to return "on time" if status is 1 and "late" if status is 0
    public static String dispStatus(int x){
        String display= "";
        if (x==1){
            display = "On Time";
        }
        else if (x == 0) {
            display = "Late";
        }
        return display;
    }
    public static void addData(int status){
        attendanceList.add(status);
    }
    public Integer[] getArrayStatus(){

        return (Integer[])attendanceList.toArray(new Integer[attendanceList.size()]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        checkInDate = findViewById(R.id.txtCheckInDate);
        checkInTime = findViewById(R.id.txtCheckInTime);
        checkOutDate = findViewById(R.id.txtCheckOutDate);
        checkOutTime = findViewById(R.id.txtCheckOutTime);
        btncheckIn = findViewById(R.id.btnCheckIn);
        btncheckOut = findViewById(R.id.btnCheckOut);
        checkInStatus = findViewById(R.id.lblStatusCheckIn);

        btncheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkin_date = checkInDate.getText().toString();
                checkin_time = checkInTime.getText().toString();
                // to set status either 1 or 0
                int status = getStatus(checkin_time);
                // to display in the label in .xml
                checkInStatus.setText(dispStatus(status));
                addData(status);

                // to insert data into database table attendance
                new Thread(() -> {
                    try {
                        MySQLDatabase newdb = new MySQLDatabase();
                        Connection con = newdb.connect();
                        String query = "INSERT INTO attendance (checkin_date, checkin_time, status) VALUES (?, ?, ?)";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setString(1, checkin_date);
                        stmt.setString(2, checkin_time);
                        stmt.setInt(3, status);

                        int rows = stmt.executeUpdate();
                        runOnUiThread(() -> {
                            if (rows > 0) {
                                Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });

                        newdb.close(con);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        btncheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout_date = checkOutDate.getText().toString();
                checkout_time = checkOutTime.getText().toString();

                // to insert data into database table attendance
                new Thread(() -> {
                    try {
                        MySQLDatabase newdb2 = new MySQLDatabase();
                        Connection con = newdb2.connect();
                        String query = "INSERT INTO attendance (checkout_date, checkout_time) VALUES (?, ?)";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setString(1, checkout_date);
                        stmt.setString(2, checkout_time);

                        int rows = stmt.executeUpdate();
                        runOnUiThread(() -> {
                            if (rows > 0) {
                                Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });

                        newdb2.close(con);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    public void back(View view) {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }
}