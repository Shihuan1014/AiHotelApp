package edu.hnu.aihotel.ui.hotelDetail;

public class RoomType {

    private String id;
    private String hotelId;
    private String hotelName;
    private String bedType;
    private String name;
    private String img;
    private String breakfast;
    private double price;
    private String bedSize;
    private String window;
    private String windowSize;
    private int num;
    private String roomSize;
    private int discount;


    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public String getBedSize() {
        return bedSize;
    }

    public void setBedSize(String bedSize) {
        this.bedSize = bedSize;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(String windowSize) {
        this.windowSize = windowSize;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", bedType='" + bedType + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", breakfast='" + breakfast + '\'' +
                ", price=" + price +
                ", bedSize='" + bedSize + '\'' +
                ", window='" + window + '\'' +
                ", windowSize='" + windowSize + '\'' +
                ", num=" + num +
                '}';
    }
}
