package kr.ac.changchang;

public class UserStatusResponse {
    private String username;
    private int grade;
    private int health;
    private int intel;
    private int stress;
    private int happiness;
    private int focus;
    private int academicAbility;
    private Title title;

    public UserStatusResponse(String username, int grade, int health, int intel, int stress, int happiness, int focus, int academicAbility, Title title) {
        this.username = username;
        this.grade = grade;
        this.health = health;
        this.intel = intel;
        this.stress = stress;
        this.happiness = happiness;
        this.focus = focus;
        this.academicAbility = academicAbility;
        this.title = title;
    }

    // 내부 클래스: Title 객체 매핑
    public static class Title {
        private String name;
        private String description;
        private String rarity;
        private String conditions;

        // Getter와 Setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRarity() {
            return rarity;
        }

        public void setRarity(String rarity) {
            this.rarity = rarity;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }
    }

    // Getter와 Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getIntel() {
        return intel;
    }

    public void setIntel(int intel) {
        this.intel = intel;
    }

    public int getStress() {
        return stress;
    }

    public void setStress(int stress) {
        this.stress = stress;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getAcademicAbility() {
        return academicAbility;
    }

    public void setAcademicAbility(int academicAbility) {
        this.academicAbility = academicAbility;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
