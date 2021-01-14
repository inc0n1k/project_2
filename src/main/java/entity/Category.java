package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    //************************************************
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "left")
    private Integer left_key;

    @NotNull
    @Column(name = "right")
    private Integer right_key;

    @NotNull
    private Integer level;

    @NotNull
    @Column(name = "name")
    private String category;

    //************************************************

    @OneToMany(mappedBy = "category")
    private List<Option> options;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
    //************************************************


    public Long getId() {
        return id;
    }

    public Integer getLeft_key() {
        return left_key;
    }

    public void setLeft_key(Integer left_key) {
        this.left_key = left_key;
    }

    public Integer getRight_key() {
        return right_key;
    }

    public void setRight_key(Integer right_key) {
        this.right_key = right_key;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
