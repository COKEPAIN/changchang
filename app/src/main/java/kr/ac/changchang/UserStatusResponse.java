package kr.ac.changchang;

import java.util.List;

import kr.ac.changchang.Title;

public class UserStatusResponse {
    private String username;
    private String password;
    private int grade;
    private int health;
    private int intel;
    private int stress;
    private int happiness;
    private int focus;
    private int academicAbility;
    private Title title; // 현재 칭호
    private List<Title> availableTitles; // 사용 가능한 칭호들

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getIntel() {
        return intel;
    }

    public void setIntel(int intel) {
        this.intel = intel;
    }

    public int getStress() {
        return stress;
    }

    public void setStress(int stress) {
        this.stress = stress;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getAcademicAbility() {
        return academicAbility;
    }

    public void setAcademicAbility(int academicAbility) {
        this.academicAbility = academicAbility;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public List<Title> getAvailableTitles() {
        return availableTitles;
    }

    public void setAvailableTitles(List<Title> availableTitles) {
        this.availableTitles = availableTitles;
    }
}
