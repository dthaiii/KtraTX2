package com.example.ktratx2;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private String email;
    private String rela;
    public Product(){

    }

    public Product(int id, String name, int price, String email, String rela) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.email = email;
        this.rela = rela;
    }

    @Override
    public String toString() {
        return id + ". " + name + " - Giá: " + price +" vnd/kg" + " - Email: " + email + " - Quan hệ: " + rela;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRela() {
        return rela;
    }

    public void setRela(String rela) {
        this.rela = rela;
    }
}
