package kr.ac.changchang;

public class TodoRequest {
    private String content;

    public TodoRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
