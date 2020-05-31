package vn.com.hotrotinhoc.convertnd;

public class convert {
    private double doF,doC;

    public convert()
    {
    }

    public double getDoF() {return this.doF;}
    public double getDoC() {return this.doC;}

    public void setDoC(double doC) {this.doC = doC;}

    public void setDoF(double doF) {this.doF = doF;}

    public void convertFtoC() {this.doC = (this.doF - 32) * 5 / 9;}

    public void converCtoF() {this.doF = this.doC * 9 / 5 + 32;}
}
