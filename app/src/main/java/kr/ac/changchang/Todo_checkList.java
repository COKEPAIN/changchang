package kr.ac.changchang;

public class Todo_checkList { // todo 리스트의 마지막 해야할 일의 대한 자바 파일
    private String text; // 들어갈 이름
    private boolean isChecked; // 체크박스 확인

    public Todo_checkList(String text, boolean isChecked) {
        this.text = text;
        this.isChecked = isChecked;
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
}