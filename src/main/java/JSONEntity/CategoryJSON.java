package JSONEntity;

import entity.Category;

public class CategoryJSON {
    private Long id;
    private int level;
    private String category;

    public CategoryJSON(Category category) {
        this.id = category.getId();
        this.level = category.getLevel();
        this.category = category.getCategory();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
