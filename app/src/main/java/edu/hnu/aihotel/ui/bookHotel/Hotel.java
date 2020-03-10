package edu.hnu.aihotel.ui.bookHotel;

public class Hotel {

    private String hotelId;
    private String name;
    private String address;
    private String areas;
    private String cover;
    private String score;
    private String hasFood;
    private String hasFitness;
    private String hasWifi;
    private String hasPark;
    private String price;
    private String link;
    private String cityNo;


    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHasFood() {
        return hasFood;
    }

    public void setHasFood(String hasFood) {
        this.hasFood = hasFood;
    }

    public String getHasFitness() {
        return hasFitness;
    }

    public void setHasFitness(String hasFitness) {
        this.hasFitness = hasFitness;
    }

    public String getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(String hasWifi) {
        this.hasWifi = hasWifi;
    }

    public String getHasPark() {
        return hasPark;
    }

    public void setHasPark(String hasPark) {
        this.hasPark = hasPark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId='" + hotelId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", areas='" + areas + '\'' +
                ", cover='" + cover + '\'' +
                ", score='" + score + '\'' +
                ", hasFood='" + hasFood + '\'' +
                ", hasFitness='" + hasFitness + '\'' +
                ", hasWifi='" + hasWifi + '\'' +
                ", hasPark='" + hasPark + '\'' +
                ", price='" + price + '\'' +
                ", link='" + link + '\'' +
                ", cityNo='" + cityNo + '\'' +
                '}';
    }
}
