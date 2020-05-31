package vn.com.hotrotinhoc.mb12;

public class AmLich {
    private String[] can = { "Canh", "Tân" , "Nhâm", "Qúy", "Giáp" , "Ất",
            "Bính", "Đinh" , "Mậu", "Kỷ"};
    private String[] chi = {"Thân" , "Dậu" , "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mẹo",
    "Thìn", "Tỵ", "Ngọ", "Mùi"};

    private int namDL;
    public AmLich(int Nam) {this.namDL = Nam;}
    public String getNamAmLich()
    {
        String can = this.can[namDL % 10];
        String chi = this.chi[namDL % 12];
        return can + " " + chi;
    }
}
