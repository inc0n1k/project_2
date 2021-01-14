package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    //************************************************

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    private Timestamp order_date = new Timestamp(new GregorianCalendar().getTimeInMillis());

    private Date order_date = new Date();

    private Boolean active;

    private Date date_complit;

    private Boolean complited;

    private String comment;

    //************************************************

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    //************************************************

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
//    public Timestamp getOrder_date() {
//        return order_date;
//    }

//    public void setOrder_date(Timestamp order_date) {
//        this.order_date = order_date;
//    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDate_complit() {
        return date_complit;
    }

    public void setDate_complit(Date date_complit) {
        this.date_complit = date_complit;
    }

    //    public Timestamp getDate_complit() {
//        return date_complit;
//    }
//    public void setDate_complit(Timestamp date_complit) {
//        this.date_complit = date_complit;
//    }

    public Boolean getComplited() {
        return complited;
    }

    public void setComplited(Boolean complited) {
        this.complited = complited;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
