package kr.ac.changchang;

public class AssignmentRequest {
    private int studentId; // int 타입으로 변경
    private String subjectName;
    private String deadline;

    // 생성자
    public AssignmentRequest(int studentId, String subjectName, String deadline) {
        this.studentId = studentId;
        this.subjectName = subjectName;
        this.deadline = deadline;
    }

    // Getter & Setter
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
