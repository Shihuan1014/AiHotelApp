package edu.hnu.aihotel.model;

public class Order {
    private String id;
    private String userId;
    private String hotelId;
    private String roomId;
    private String startDate;
    private String endDate;
    private Double price;
    private String guests;
    private String phones;
    private String arriveTime;
    private int roomNum;
    private int payStatus;
    private String payWay;

    public Order(){

    }

    public Order(String id, String userId, String hotelId, String roomId, String startDate, String endDate, Double price, String guests, String phones, String arriveTime, int roomNum, String payWay) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.guests = guests;
        this.phones = phones;
        this.arriveTime = arriveTime;
        this.roomNum = roomNum;
        this.payWay = payWay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", price=" + price +
                ", guests='" + guests + '\'' +
                ", phones='" + phones + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", roomNum=" + roomNum +
                ", payStatus=" + payStatus +
                ", payWay='" + payWay + '\'' +
                '}';
    }
}
