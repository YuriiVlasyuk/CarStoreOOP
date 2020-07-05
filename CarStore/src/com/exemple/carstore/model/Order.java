package com.exemple.carstore.model;

public class Order {

    private long id;
    private long customerId; // кому продали
    private long employeeId; // хто продав
    private long[] cars; // список машин які продали

    public Order(long id, long customerId, long employeeId, long[] cars) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.cars = cars;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long[] getCars() {
        return cars;
    }

    public void setCars(long[] cars) {
        this.cars = cars;
    }
}
