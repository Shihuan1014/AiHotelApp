package edu.hnu.aihotel.ui.social;

public class SocialNews {

    public SocialNews(){

    }

    public SocialNews(String userId, String userName, String content,
                      String pictures,
                      String avatar, String time, String likeCount,
                      String shareCount, String commentCount) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.avatar = avatar;
        this.time = time;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.commentCount = commentCount;
        this.pictures = pictures;
    }

    private String userId;
    private String userName;
    private String content;
    private String avatar;
    private String time;
    private String likeCount;
    private String shareCount;
    private String commentCount;
    private String pictures;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "SocialNews{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", time='" + time + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", shareCount='" + shareCount + '\'' +
                ", commentCount='" + commentCount + '\'' +
                '}';
    }
}
