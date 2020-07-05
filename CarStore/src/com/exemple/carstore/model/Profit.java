package com.exemple.carstore.model;

public class Profit {
    private int count; // кількість проданих машин
    private double price; // загальна сума

    public Profit(int count, double price) {
        this.count = count;
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " всього: " + count +
                " машин на сумму: " + price;
    }
}
