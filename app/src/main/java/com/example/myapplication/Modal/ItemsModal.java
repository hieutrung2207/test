package com.example.myapplication.Modal;

import java.util.Date;

public class ItemsModal {
    private int pid;
    private String name;
    private Double price;
    private String brand;
    private Date dob;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public ItemsModal(int pid, String name, Double price, String brand, Date dob) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.dob = dob;
    }

    public ItemsModal() {

    }

    @Override
    public String toString() {
        return "ItemsModal{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", dob=" + dob +
                '}';
    }
}
