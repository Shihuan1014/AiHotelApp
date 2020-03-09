package edu.hnu.aihotel.ui.bookHotel;

public class Hotel {
    private String coverUrl;
    private String address;
    private String name;
    private String hotelUrl;
    private String minCost;

    public Hotel(){

    }

    public Hotel(String coverUrl,String address,String name,String minCost){
        this.coverUrl = coverUrl;
        this.address = address;
        this.name = name;
        this.minCost = minCost;
    }


    public String getMinCost() {
        return minCost;
    }

    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelUrl() {
        return hotelUrl;
    }

    public void setHotelUrl(String hotelUrl) {
        this.hotelUrl = hotelUrl;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "coverUrl='" + coverUrl + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", hotelUrl='" + hotelUrl + '\'' +
                '}';
    }
}
