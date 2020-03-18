package edu.hnu.aihotel.model;

public class FaceRegisterInfo {
    private byte[] featureData;
    private String name;
    private String password;

    public FaceRegisterInfo(byte[] faceFeature, String name) {
        this.featureData = faceFeature;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFeatureData() {
        return featureData;
    }

    public void setFeatureData(byte[] featureData) {
        this.featureData = featureData;
    }
}
