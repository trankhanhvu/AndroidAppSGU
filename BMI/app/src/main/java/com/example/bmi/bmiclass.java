package com.example.bmi;

public class bmiclass {
    private double h, w;
    public bmiclass(double h, double w)
    {
        setValues(h,w);
    }

    public void setValues(double h, double w)
    {
        this.h =h;
        this.w = w;
    }
    public double getBMI()
    {
        double bmi = w/ (h*h);
        return bmi;
    }
    public String getChanDoan()
    {
        double bmi = this.getBMI();
        String kq="";
        if(bmi < 18) kq ="gầy";
        else if(bmi <= 24.9) kq ="bình thường";
        else if(bmi <=29.9) kq ="bép phì độ I";
        else if(bmi <=34.9) kq = "béo phì độ II";
        else kq ="béo phì độ III";
        return kq;
    }
}
