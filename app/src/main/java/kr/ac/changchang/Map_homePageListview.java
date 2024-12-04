package kr.ac.changchang;

public class Map_homePageListview {
    private String type; // 공지 유형
    private String title; // 제목
    private String link; // 링크

    public Map_homePageListview(String type, String title, String link) {
        this.type = type;
        this.title = title;
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
