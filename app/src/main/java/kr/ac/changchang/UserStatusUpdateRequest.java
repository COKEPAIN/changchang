package kr.ac.changchang;

public class UserStatusUpdateRequest {
    private int grade;
    private int stress;
    private int happiness;
    private int focus;
    private int academicAbility;

    // 생성자
    public UserStatusUpdateRequest(int grade, int stress, int happiness, int focus, int academicAbility) {
        this.grade = grade;
        this.stress = stress;
        this.happiness = happiness;
        this.focus = focus;
        this.academicAbility = academicAbility;
    }

    // Getter & Setter
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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
}
