package edu.hnu.aihotel.ui.bookHotel;

public class Area {

    private String areaName;
    private String areaPrice;

    public Area(){

    }

    public Area(String areaName,String areaPrice){
        this.areaName = areaName;
        this.areaPrice = areaPrice;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaPrice() {
        return areaPrice;
    }

    public void setAreaPrice(String areaPrice) {
        this.areaPrice = areaPrice;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaName='" + areaName + '\'' +
                ", areaPrice='" + areaPrice + '\'' +
                '}';
    }
}
