package com.webcuoiky.softwareshop.model;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Entity
@Table
public class Order_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer order_quantity;

    public Integer getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(Integer order_quantity) {
        this.order_quantity = order_quantity;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "software_id")
    private Software software;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public String getFormattedCost(float originalCost) {

        /* Code cũ
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(cost); */

        DecimalFormatSymbols customSymbol = new DecimalFormatSymbols();
        customSymbol.setGroupingSeparator('.');//Đổi dấu "," thành "."

        DecimalFormat integerFormat = new DecimalFormat("#,###", customSymbol);
        String formatedInteger = integerFormat.format(originalCost);
        System.out.println(formatedInteger + ".000");

        return formatedInteger + ".000"; //1000.000 -> 1.000.000
    }
}
