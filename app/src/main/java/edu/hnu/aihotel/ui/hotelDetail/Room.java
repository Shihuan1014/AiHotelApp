package edu.hnu.aihotel.ui.hotelDetail;

public class Room {
    private String roomId;
    private String coverUrl;
    private String roomName;
    private String size;
    private String bedSize;
    private String window;
    private String price;

    public Room(){

    }

    public Room(String roomName, String coverUrl, String size, String bedSize, String window, String price) {
        this.coverUrl = coverUrl;
        this.roomName = roomName;
        this.size = size;
        this.bedSize = bedSize;
        this.window = window;
        this.price = price;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", roomName='" + roomName + '\'' +
                ", size='" + size + '\'' +
                ", bedSize='" + bedSize + '\'' +
                ", window='" + window + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
