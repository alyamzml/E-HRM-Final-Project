package com.emp;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.emp.databinding.ActivityMainBinding;
import com.emp.databinding.ActivityRegisterBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends AppCompatActivity {

    // Declare private fields to store user registration data
    private String fname;
    private String lname;
    private String fullname;
    private String matric_id;
    private String emel;
    private String password;
    private String address;
    private String phoneNo;
    private String position;
    private String performance;

    // Declare binding object for Register activity
    @NonNull
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate layout for Register activity using binding object
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Register button click listener to capture user input and save it to database
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input from EditText fields
                fname = binding.txtfname.getText().toString();
                lname = binding.txtlname.getText().toString();

                // Create Name object to combine first name and last name into full name
                Name n = new Name(fname,lname);
                fullname = n.getFullname();

                // Create Performance object to get status value for new user
                Performance p = new Performance();
                performance = p.getStatus();

                matric_id = binding.txtid.getText().toString();
                emel = binding.txtemel.getText().toString();
                password = binding.txtPassword.getText().toString();
                phoneNo = binding.txtphone.getText().toString();
                address = binding.txtaddress.getText().toString();
                position = binding.txtposition.getText().toString();

                // Open new thread to execute database query for inserting user data
                new Thread(() -> {
                    try {
                        // Connect to database
                        MySQLDatabase OT = new MySQLDatabase();
                        Connection con = OT.connect();

                        // Prepare SQL query for inserting user data
                        String query = "INSERT INTO profile (NAME, MATRIC_ID, EMAIL, PASSWORD, PHONE_NO, ADDRESS, POSITION, PERFORMANCE) VALUES (?,?,?,?,?,?,?,?)";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setString(1, fullname);
                        stmt.setString(2, matric_id);
                        stmt.setString(3, emel);
                        stmt.setString(4, password);
                        stmt.setString(5, phoneNo);
                        stmt.setString(6, address);
                        stmt.setString(7, position);
                        stmt.setString(8, performance);

                        // Execute SQL query to insert user data and show success or error message
                        int rows = stmt.executeUpdate();
                        runOnUiThread(() -> {
                            if (rows > 0) {
                                binding.txtStatus.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                i.putExtra("fname", fname);
                                i.putExtra("lname", lname);
                                startActivity(i);
                            } else {
                                binding.txtStatus.setVisibility(View.VISIBLE);
                                binding.txtStatus.setText("Error occurred");
                                Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Close database connection
                        OT.close(con);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    // Back button click listener to navigate back to Login activity
    public void back(View view) {
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }

}