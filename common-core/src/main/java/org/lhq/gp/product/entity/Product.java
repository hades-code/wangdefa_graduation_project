package org.lhq.gp.product.entity;

import java.math.BigDecimal;

/**
 * @program: wangdefa_graduation_project
 * @description: 产品
 * @author: Wang defa
 * @create: 2020-09-15 15:02
 */


public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer count;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
