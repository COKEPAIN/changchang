package kr.ac.changchang;

public class Schedule {
    private String dayOfWeek; // 요일
    private String startTime; // 시작 시간
    private String endTime;   // 종료 시간

    // 기본 생성자
    public Schedule() {
    }

    // 모든 필드를 초기화하는 생성자
    public Schedule(String dayOfWeek, String startTime, String endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter와 Setter
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "Schedule{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
