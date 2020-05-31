package com.example.namamlich;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.com.hotrotinhoc.mb12.AmLich;

public class MainActivity extends AppCompatActivity {

    Button btnXem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnXem = (Button)findViewById(R.id.btnXem);
        btnXem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                XemNamAL(v);
            }
        });
    }

    public void XemNamAL(View view)
    {
        EditText namDL = (EditText)findViewById(R.id.txtNam);
        int nam = Integer.parseInt(namDL.getText().toString());
        AmLich al = new AmLich(nam);
        String amlich = al.getNamAmLich();
        TextView tv = (TextView)findViewById(R.id.txtKetQua);
        tv.setText(amlich);
    }
}
