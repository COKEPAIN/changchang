package kr.ac.changchang;

public class Todo_assignmentRequest {
    private String title;
    private String description;

    public Todo_assignmentRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

