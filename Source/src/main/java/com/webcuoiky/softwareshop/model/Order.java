package com.webcuoiky.softwareshop.model;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float cost;
    private String name;
    private String email;
    private String status;
    private String address;
    private String phone;
    private Date date;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Collection<Payment> payments;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Collection<Order_items> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Collection<Payment> payments) {
        this.payments = payments;
    }

    public Collection<Order_items> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<Order_items> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFormattedCost() {

        /* Code cũ
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(cost); */

        DecimalFormatSymbols customSymbol = new DecimalFormatSymbols();
        customSymbol.setGroupingSeparator('.');//Đổi dấu "," thành "."

        DecimalFormat integerFormat = new DecimalFormat("#,###", customSymbol);
        String formatedInteger = integerFormat.format(cost);
        System.out.println(formatedInteger + ".000");

        return formatedInteger + ".000"; //1000.000 -> 1.000.000
    }

}
