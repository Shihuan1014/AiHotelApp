package edu.hnu.aihotel.ui.food;

public class LikeFood {
    String imageUrl;
    String foodName;
    String type;
    String price;

    public LikeFood(){

    }

    public LikeFood(String imageUrl, String foodName, String type, String price) {
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.type = type;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "LikeFood{" +
                "imageUrl='" + imageUrl + '\'' +
                ", foodName='" + foodName + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
