package com.emp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.*;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView lblName = findViewById(R.id.lblName);
        TextView lblID = findViewById(R.id.lblID);
        TextView lblEmel = findViewById(R.id.lblEmel);
        TextView lblPhone = findViewById(R.id.lblPhone);
        TextView lblAddr = findViewById(R.id.lblAddr);
        TextView lblPosition = findViewById(R.id.lblPosition);
        TextView lblPerformance = findViewById(R.id.lblPerform);

        // to get data from database
        new Thread(() -> {
            try {
                MySQLDatabase newdb = new MySQLDatabase();
                Connection con = newdb.connect();
                String query = "select * from profile";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    lblName.setText(rs.getString(1));
                    lblID.setText(rs.getString(2));
                    lblEmel.setText(rs.getString(3));
                    lblPhone.setText(rs.getString(5));
                    lblAddr.setText(rs.getString(6));
                    lblPosition.setText(rs.getString(7));
                    Performance p = new Performance();
                    lblPerformance.setText(p.getStatus());
                }

                newdb.close(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void back(View view) {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }
}