package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.repository.TestRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TestRepository(getApplicationContext());
    }
}
