package com.emp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.emp.databinding.ActivityLoginBinding;

import java.sql.*;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the input values from the EditText
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                if (isValidEmail(email) && password.length() > 0) {
                    // Show the progress bar
                    binding.progressBar.setVisibility(View.VISIBLE);

                    // to get data from database table profile
                    new Thread(() -> {
                        try {
                            MySQLDatabase newdb = new MySQLDatabase();
                            Connection con = newdb.connect();
                            String query = "SELECT * FROM profile WHERE email = ? AND password = ?";
                            PreparedStatement stmt = con.prepareStatement(query);
                            stmt.setString(1, email);
                            stmt.setString(2, password);

                            ResultSet rs = stmt.executeQuery();
                            boolean exists = rs.next();
                            runOnUiThread(() -> {
                                //Hide the progress bar
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                if (exists) {
                                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                                }
                            });
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email and password", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // navigate to register class if sign up label is clicked
        binding.goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Add your own email validation logic here
        return email.contains("@");
    }
}