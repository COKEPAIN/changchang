package kr.ac.changchang;

import java.util.List;

public class Subject {
    private String subjectName;      // 과목명
    private List<Schedule> schedules; // 수업 일정 리스트

    // 기본 생성자
    public Subject() {
    }

    // 모든 필드를 초기화하는 생성자
    public Subject(String subjectName, List<Schedule> schedules) {
        this.subjectName = subjectName;
        this.schedules = schedules;
    }

    // Getter와 Setter
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "Subject{" +
                "subjectName='" + subjectName + '\'' +
                ", schedules=" + schedules +
                '}';
    }
}
