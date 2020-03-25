package edu.hnu.aihotel.ui.bookHotel;

public class Hotel {


    private String id;
    private String name;
    private String address;
    private int dpcount;
    private int dpscore;
    private String img;
    private String isSingleRec;
    private String lat;
    private String lon;
    private double score;
    private String shortName;
    private String star;
    private String stardesc;
    private String url;
    private String cityNo;
    private String openYear;
    private String tel;
    private double minPrice;
    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOpenYear() {
        return openYear;
    }

    public void setOpenYear(String openYear) {
        this.openYear = openYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getDpcount() {
        return dpcount;
    }

    public void setDpcount(int dpcount) {
        this.dpcount = dpcount;
    }

    public int getDpscore() {
        return dpscore;
    }

    public void setDpscore(int dpscore) {
        this.dpscore = dpscore;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIsSingleRec() {
        return isSingleRec;
    }

    public void setIsSingleRec(String isSingleRec) {
        this.isSingleRec = isSingleRec;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStardesc() {
        return stardesc;
    }

    public void setStardesc(String stardesc) {
        this.stardesc = stardesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dpcount=" + dpcount +
                ", dpscore=" + dpscore +
                ", img='" + img + '\'' +
                ", isSingleRec='" + isSingleRec + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", score=" + score +
                ", shortName='" + shortName + '\'' +
                ", star='" + star + '\'' +
                ", stardesc='" + stardesc + '\'' +
                ", url='" + url + '\'' +
                ", cityNo='" + cityNo + '\'' +
                '}';
    }
}
