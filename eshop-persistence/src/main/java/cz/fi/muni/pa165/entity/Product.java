package cz.fi.muni.pa165.entity;


import cz.fi.muni.pa165.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name="ESHOP_PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false,unique=true)
    private String name;

    @Column
    private Color color;

    @Column
    @Temporal(TemporalType.DATE)
    private Date addedDate;

    public Product(Long id) {
        this.id = id;
    }

    public Product(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;

        if (o instanceof Product) {
            Product product = (Product) o;
            return this.name.equals(product.getName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
