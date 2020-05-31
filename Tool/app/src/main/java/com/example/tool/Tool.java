package com.example.tool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tool extends AppCompatActivity {

    Button btnDoiNhietDo,btnNamAmLich,btnBMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDoiNhietDo = (Button)findViewById(R.id.btnDoinhietdo);
        btnDoiNhietDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoiNhietDo.class));
            }
        });

        btnNamAmLich = (Button)findViewById(R.id.btnNamamlich);
        btnNamAmLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NamAmLich.class));
            }
        });

        btnBMI = (Button)findViewById(R.id.btnBMI);
        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BMI.class));
            }
        });
    }
}
