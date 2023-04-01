package com.emp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.emp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    // navigate to login page once the button is clicked
    public void start(View view) {
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }
}