package com.example.tool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BMI extends AppCompatActivity {

    EditText hoten, chieucao, cannang, chisobmi, chandoan;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);
        hoten = (EditText)findViewById(R.id.txtTen);
        chieucao = (EditText)findViewById(R.id.txtChieucao);
        cannang = (EditText)findViewById(R.id.txtCannag);
        chisobmi = (EditText)findViewById(R.id.txtBMI);
        chandoan = (EditText)findViewById(R.id.txtChuandoan);
        bt = (Button)findViewById(R.id.btnTinh);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinh();
            }
        });
    }

    public void tinh()
    {
        double h = Double.parseDouble(chieucao.getText().toString());
        double w = Double.parseDouble(cannang.getText().toString());
        bmiclass obj = new bmiclass(h,w);
        double bmi = obj.getBMI();
        String kq = obj.getChanDoan();
        chisobmi.setText(String.valueOf(bmi));
        chandoan.setText(kq);
    }

}
