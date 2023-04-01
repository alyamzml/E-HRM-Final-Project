package com.emp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Leave extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        EditText editTextStartDate = findViewById(R.id.TextSDate_Leave);
        EditText editTextEndDate = findViewById(R.id.TextEDate_Leave);
        EditText editTextDays = findViewById(R.id.TextDays_Leave);
        EditText editTextType = findViewById(R.id.TextType_Leave);
        Button button = findViewById(R.id.btnAdd_Leave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String startDate = editTextStartDate.getText().toString();
                String endDate = editTextEndDate.getText().toString();
                String days = editTextDays.getText().toString();
                String type = editTextType.getText().toString();

                // to insert data into database table hrm_leave
                new Thread(() -> {
                    try {
                        MySQLDatabase newdb = new MySQLDatabase();
                        Connection con = newdb.connect();
                        String query = "INSERT INTO hrm_leave (start_date, end_date, days, type) VALUES (?, ?,?,?)";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setString(1, startDate);
                        stmt.setString(2, endDate);
                        stmt.setString(3, days);
                        stmt.setString(4, type);

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
    }

    public void Back(View view) {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }
}