package kr.ac.changchang;

public class Todo_assignmentRespones {
    private String subjectName;
    private boolean submitted;
    private String deadline;

    public String getSubjectName() {
        return subjectName;
    }

    public boolean getSubmitted() {
        return submitted;
    }

    public String getDeadline() {
        return deadline;
    }

    public Todo_assignmentRespones(String subjectName, boolean submitted, String deadline){
        this.subjectName = subjectName;
        this.submitted = submitted;
        this.deadline = deadline;
    }
}

