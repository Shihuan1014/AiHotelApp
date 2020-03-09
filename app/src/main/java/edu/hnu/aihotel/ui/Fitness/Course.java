package edu.hnu.aihotel.ui.Fitness;

public class Course {

    private String courseName;
    private String cover;
    private String level;
    private String courseNum;
    private String cost;
    private String people;

    public Course(){

    }

    public Course(String courseName, String cover, String level, String courseNum, String cost, String people) {
        this.courseName = courseName;
        this.cover = cover;
        this.level = level;
        this.courseNum = courseNum;
        this.cost = cost;
        this.people = people;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", cover='" + cover + '\'' +
                ", level='" + level + '\'' +
                ", courseNum='" + courseNum + '\'' +
                ", cost='" + cost + '\'' +
                ", people='" + people + '\'' +
                '}';
    }
}
