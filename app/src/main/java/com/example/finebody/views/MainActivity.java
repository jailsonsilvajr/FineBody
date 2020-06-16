package com.example.finebody.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.finebody.R;
import com.example.finebody.repository.TestRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TestRepository(getApplicationContext());
    }
}
