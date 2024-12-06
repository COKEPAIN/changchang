package kr.ac.changchang;

public class Todo_checkList {
    private String text;
    private boolean isChecked;
    private int id; // ID 필드 추가

    public Todo_checkList(String text, boolean isChecked, int id) {
        this.text = text;
        this.isChecked = isChecked;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() { // ID getter 추가
        return id;
    }

    public void setId(int id) { // ID setter 추가
        this.id = id;
    }
}
