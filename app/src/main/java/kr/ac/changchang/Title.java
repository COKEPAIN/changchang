package kr.ac.changchang;

public class Title {
    private String name;
    private String description;
    private String rarity;
    private String conditions;

    // Constructor
    public Title(String name, String description, String rarity, String conditions) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.conditions = conditions;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rarity='" + rarity + '\'' +
                ", conditions='" + conditions + '\'' +
                '}';
    }
}
