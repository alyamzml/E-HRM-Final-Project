package com.emp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // go to profile class
    public void toProfile(View view) {
        Intent i = new Intent(getApplicationContext(), Profile.class);
        startActivity(i);
    }

    // go to overtime class
    public void toOvertime(View view) {
        Intent i = new Intent(getApplicationContext(), OverTime.class);
        startActivity(i);
    }

    // go to leave class
    public void toLeave(View view) {
        Intent i = new Intent(getApplicationContext(), Leave.class);
        startActivity(i);
    }

    // go to attendance class
    public void toAttend(View view) {
        Intent i = new Intent(getApplicationContext(), Attendance.class);
        startActivity(i);
    }

    // go to payroll class
    public void toPayroll(View view) {
        Intent i = new Intent(getApplicationContext(), Payroll_Main.class);
        startActivity(i);
    }

    public void toOut(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}