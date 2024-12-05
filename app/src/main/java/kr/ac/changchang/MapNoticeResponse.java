package kr.ac.changchang;

public class MapNoticeResponse {
    private int id;       // 공지 ID
    private String title; // 공지 제목
    private String link;  // 공지 링크
    private String type;  // 공지 유형

    // 기본 생성자
    public MapNoticeResponse() {
    }

    // 모든 필드를 초기화하는 생성자
    public MapNoticeResponse(int id, String title, String link, String type) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.type = type;
    }

    // Getter와 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString() 메서드 (디버깅에 유용)
    @Override
    public String toString() {
        return "MapNoticeResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
