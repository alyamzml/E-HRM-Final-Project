package com.emp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.*;

public class Payroll_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payrollmain);
        Button button = findViewById(R.id.btnDisplay);
        TextView textIncome = findViewById(R.id.textView8);
        TextView textDeduct = findViewById(R.id.textView9);
        TextView textPayroll = findViewById(R.id.textView10);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                // add data to payrollList
                Payroll2 january = new Payroll2();
                double a = january.calcIncome();
                double b = january.calcDeduct();
                january.calcPayroll(a,b);
                PayrollList payrolllist = new PayrollList();
                payrolllist.add(january);

                TextView textIncome = findViewById(R.id.textView8);
                textIncome.setText(january.displayIncome());
                TextView textDeduct = findViewById(R.id.textView9);
                textDeduct.setText(january.displayDeduct());
                TextView textPayroll = findViewById(R.id.textView10);
                textPayroll.setText(january.displayTotal());
            }
        });
    }

    public void Back(View view) {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }
}