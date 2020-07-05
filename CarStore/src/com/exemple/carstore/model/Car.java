package com.exemple.carstore.model;

public class Car {

    private long id;
    private String model;
    private double price;
    private CarModel genre;

    public Car(long id, String model, double price, CarModel genre) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CarModel getGenre() {
        return genre;
    }

    public void setGenre(CarModel genre) {
        this.genre = genre;
    }
}
