package com.emp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class OverTime extends AppCompatActivity {

    private EditText editTextDate;
    private EditText editTextTime;
    private String date, time;
    private double totalOTHour =0.0;
    private ArrayList<Double> OTHours = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_time);
        Button button = findViewById(R.id.btnAdd);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = editTextDate.getText().toString().trim();
                time = editTextTime.getText().toString().trim();

                if (date.isEmpty() || time.isEmpty()) {
                    Toast.makeText(OverTime.this, "Please enter a date and a time", Toast.LENGTH_SHORT).show();
                } else {

                    // insert data into database table overtime
                    new Thread(() -> {
                        try {
                            OTHours.add(Double.parseDouble(time));

                            MySQLDatabase newdb = new MySQLDatabase();
                            Connection con = newdb.connect();
                            String query = "INSERT INTO overtime (OT_DATE, OT_HOUR) VALUES (?, ?)";
                            PreparedStatement stmt = con.prepareStatement(query);
                            stmt.setString(1, date);
                            stmt.setString(2, time);

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
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        // Get current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(OverTime.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Update editTextDate with selected date
                        editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        // Show date picker dialog
        datePickerDialog.show();
    }

    // to calculate total hour of overtime by fetching data from database table overtime
    public double getTotalTime() {
        new Thread(() -> {
            try {
                MySQLDatabase newdb = new MySQLDatabase();
                Connection con = newdb.connect();
                String query = "select sum(OT_HOUR) from overtime";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                totalOTHour = rs.getInt(1);
                System.out.print(totalOTHour);
                newdb.close(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }).start();
        return totalOTHour;
}

    public void back(View view) {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }

    public String getDate(){
        return date;
    }
}


