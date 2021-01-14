package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "products")
public class Product implements Comparable<Product> {
    @Override
    public int compareTo(Product product) {
        return name.compareTo(product.getName());
    }

    //***********************************
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Is Null")
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull(message = "Is Null")
    private String name;

    @NotNull(message = "Is Null")
    private Integer price;
    //***********************************

    @OneToMany(mappedBy = "product")
    private List<Value> values;

    @OneToMany(mappedBy = "product")
    private  List<OrderProduct> orderProducts;

    //***********************************

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

}
